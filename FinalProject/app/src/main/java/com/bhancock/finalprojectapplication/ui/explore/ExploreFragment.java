package com.bhancock.finalprojectapplication.ui.explore;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bhancock.finalprojectapplication.PermissionUtils;
import com.bhancock.finalprojectapplication.R;
import com.bhancock.finalprojectapplication.model.User;
import com.bhancock.finalprojectapplication.model.UserLocation;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.maps.DirectionsApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.google.maps.internal.PolylineEncoding;
import com.google.maps.model.DirectionsResult;
import com.google.maps.model.DirectionsRoute;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExploreFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.OnPolylineClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback {

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;

    private LocationCallback mLocationCallback;
    private LocationRequest mLocationRequest;

    private static final String TAG = ExploreFragment.class.getSimpleName();

    private FusedLocationProviderClient mFusedLocationProviderClient;

    public static final int REQUEST_CODE_PERMISSIONS = 101;
    private ExploreViewModel exploreViewModel;
    private GoogleMap mMap;
    private double latitude;
    private double longitude;
    private UserLocation mUserLocation;
    private FirebaseFirestore firebaseFirestore;
    private boolean isMapReady;
    private boolean userInteractingWithMap;
    private LocationBroadCastReceiver locationBroadCastReceiver;
    boolean updatingCameraToLastKnownPosition;
    private GeoApiContext geoApiContext;
    private GeoApiContext directionsGeoApiContext;
    PlacesClient placesClient;
    ExtendedFloatingActionButton getDirectionsButton;
    private String directionsKeyAPI;
    private List<Marker> mapMarkers = new ArrayList<>();



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate() called....");

        firebaseFirestore = FirebaseFirestore.getInstance();
        mUserLocation = new UserLocation();
        userInteractingWithMap = false;

        // Initialize the SDK
        Places.initialize(getActivity().getApplicationContext(), getString(R.string.google_places_key));
        // Create a new Places client instance
        placesClient = Places.createClient(getActivity().getApplicationContext());



        locationBroadCastReceiver = new LocationBroadCastReceiver();
        IntentFilter intentFilter = new IntentFilter("ACTION_LOCATION_DATA");

        getActivity().registerReceiver(locationBroadCastReceiver, intentFilter);

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                Log.d(TAG, "LocationCallback");
                if (locationResult == null) {
                    Toast.makeText(getContext(), "Check Location Permissions", Toast.LENGTH_SHORT).show();
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    Log.d(TAG, "start location method call: Latitude: " + location.getLatitude());
                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))
                            .zoom(18).bearing(0).tilt(70).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        };
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        Log.d(TAG, "OnCreateView");

        exploreViewModel = new ViewModelProvider(this).get(ExploreViewModel.class);
        View root = inflater.inflate(R.layout.fragment_explore, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getChildFragmentManager().findFragmentById(R.id.map);


        getDirectionsButton = root.findViewById(R.id.get_directions_fab);
        getDirectionsButton.hide();


        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        mapFragment.getMapAsync(this);
        isMapReady = true;


        AutocompleteSupportFragment autocompleteSupportFragment = (AutocompleteSupportFragment)
        getChildFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        // Specify the types of place data to return.
        autocompleteSupportFragment.setPlaceFields(Arrays.asList(Place.Field.ID,
                Place.Field.NAME,
                Place.Field.LAT_LNG,
                Place.Field.ADDRESS,
                Place.Field.USER_RATINGS_TOTAL,
                Place.Field.RATING,
                Place.Field.PHOTO_METADATAS,
                Place.Field.WEBSITE_URI));

        autocompleteSupportFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                Log.i(TAG, "Place latitude: " + place.getLatLng().latitude);
                Log.i(TAG, "Place longitude: " + place.getLatLng().longitude);

                updateCameraToSearchLocation(place.getLatLng().latitude,
                                             place.getLatLng().longitude,
                                             place.getName());
            }
            @Override
            public void onError(@NonNull Status status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: " + status);
            }
        });
        return root;
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();

        getActivity().unregisterReceiver(locationBroadCastReceiver);
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setBuildingsEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();

        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveStartedListener(this);
        mMap.setOnCameraMoveListener(this);
        mMap.setOnCameraMoveCanceledListener(this);
        mMap.setOnPolylineClickListener(this);

        updateCameraToLastKnownPosition();

        if(geoApiContext == null) {
            geoApiContext = new GeoApiContext.Builder()
                    .apiKey(getString(R.string.google_maps_key)).build();
        }

        if(directionsGeoApiContext == null) {
            directionsGeoApiContext = new GeoApiContext.Builder().apiKey(getString(R.string.google_directions_key)).build();
        }
    }


    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        Log.d(TAG, "You are here!!");

        // [START maps_check_location_permission]
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            Log.d(TAG, "You are now here!!");
            if (mMap != null) {

                Log.d(TAG, "You are now in here!!");
                mMap.setMyLocationEnabled(true);

            }
        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission((AppCompatActivity) getActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
        // [END maps_check_location_permission]
    }

    @Override
    public boolean onMyLocationButtonClick() {
        Toast.makeText(getContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show();

        updateCameraToLastKnownPosition();

        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getContext(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        Log.d(TAG, "Polyline selected!");
        polyline.setColor(R.color.colorPrimary);
        polyline.setZIndex(1);
    }

    @SuppressLint("MissingPermission")
    private boolean updateCameraToLastKnownPosition() {
        updatingCameraToLastKnownPosition = true;
        mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {

                    CameraPosition cameraPosition = new CameraPosition.Builder()
                            .target(new LatLng(location.getLatitude(), location.getLongitude()))
                            .zoom(18).bearing(0).tilt(70).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
            }
        });

        Log.d(TAG, "updateCameraToLastKnownPosition: " + true);
        return updatingCameraToLastKnownPosition;
    }

    private void updateCameraToSearchLocation(final double latitude,
                                              final double longitude,
                                              final String title) {

        removePreviousMarkersFromMap();

        double bottomBoundary = latitude - 1;
        double leftBoundary = longitude - 1;
        double topBoundary = latitude + 1;
        double rightBoundary = longitude + 1;

        LatLng upperRightCoordinate = new LatLng(topBoundary, rightBoundary);
        LatLng lowerLeftCoordinate = new LatLng(bottomBoundary, leftBoundary);

        LatLngBounds latLngBounds = new LatLngBounds.Builder()
                .include(upperRightCoordinate)
                .include(lowerLeftCoordinate)
                .build();

//        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 50));

        final LatLng markerPosition = new LatLng(latitude, longitude);
        MarkerOptions markerOptions = new MarkerOptions().position(markerPosition).title(title);

        mMap.addMarker(markerOptions);

        if (!userInteractingWithMap && !updatingCameraToLastKnownPosition) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(latitude, longitude))
                    .zoom(18).bearing(0).tilt(70).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

        //TODO: Animate button appearance..fade in when camera arrives at search location
        //TODO: Animate hide when user gets directions
        getDirectionsButton.show();

        getDirectionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDirections(markerPosition);
                getDirectionsButton.hide();
                String latitude = String.valueOf(markerPosition.latitude);
                String longitude = String.valueOf(markerPosition.longitude);
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
                Intent googleMapsIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                googleMapsIntent.setPackage("com.google.android.apps.maps");
//                startActivity(googleMapsIntent);
            }
        });
    }

    private void removePreviousMarkersFromMap() {
        for (Marker marker : mapMarkers) {

            marker.remove();
        }
    }



    @Override
    public void onCameraIdle() {

        new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                userInteractingWithMap = false;
                updatingCameraToLastKnownPosition = false;
                Log.d(TAG, "updatingCameraToLastKnownPosition: " + false);
            }
        }.start();
    }

    @Override
    public void onCameraMoveCanceled() {

    }

    @Override
    public void onCameraMove() {

    }

    @Override
    public void onCameraMoveStarted(int reason) {
        if(reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            Log.d(TAG, "The user gestured on the map.");
            userInteractingWithMap = true;
            Log.d(TAG, "userInteractingWithMap is: " + true);

        } else if(reason == GoogleMap.OnCameraMoveStartedListener.REASON_API_ANIMATION) {
            Log.d(TAG, "The user tapped something on the map.");
            userInteractingWithMap = true;
            Log.d(TAG, "userInteractingWithMap is: " + true);

        } else if(reason == GoogleMap.OnCameraMoveStartedListener.REASON_DEVELOPER_ANIMATION) {
            Log.d(TAG, "I'm moving the map");

            if(updatingCameraToLastKnownPosition) {
                Log.d(TAG, "updatingCameraToLastKnownPosition: " + true);
            }

            userInteractingWithMap = false;
            Log.d(TAG, "userInteractingWithMap is: " + false);
        }
    }

    private void getDirections(LatLng latLng) {
        DirectionsApiRequest directionsApiRequest = new DirectionsApiRequest(directionsGeoApiContext);
        directionsApiRequest.alternatives(true);

        Log.d(TAG, "getDirections(): mUserLocation Latitude: " + mUserLocation.getGeoPoint().getLatitude());
        Log.d(TAG, "getDirections(): mUserLocation Longitude: " + mUserLocation.getGeoPoint().getLatitude());

        directionsApiRequest.departureTimeNow();
        com.google.maps.model.LatLng latLngOriginPosition = new com.google.maps.model.LatLng(
                mUserLocation.getGeoPoint().getLatitude(),
                mUserLocation.getGeoPoint().getLongitude());
        directionsApiRequest.origin(latLngOriginPosition);


        com.google.maps.model.LatLng destination = new com.google.maps.model.LatLng(latLng.latitude, latLng.longitude);

        Log.d(TAG, "getDirections(): destination Latitude: " + destination.lat);
        Log.d(TAG, "getDirections(): destination Longitude: " + destination.lng);

        Log.d(TAG, "getDirections: destination: " + destination.toString());
        directionsApiRequest.destination(destination);

        directionsApiRequest.setCallback(new PendingResult.Callback<DirectionsResult>() {
            @Override
            public void onResult(DirectionsResult result) {
                Log.d(TAG, "result: " + result.geocodedWaypoints);
                Log.d(TAG, "result routes: " + result.routes[0].legs[0].duration);

                getPolylinesFromResult(result);
            }

            @Override
            public void onFailure(Throwable e) {

            }
        });

        final String googleMapsDestination = destination.toString();
        final String googleMapsOrigin = latLngOriginPosition.toString();

        Log.d(TAG, "google maps destination" + googleMapsDestination);
        Log.d(TAG, "google maps origin: " + googleMapsOrigin);


//        directionsKeyAPI = getString(R.string.google_directions_key);
//
//        final OkHttpClient okHttpClient = new OkHttpClient();
//        final Request request = new Request.Builder()
//                .url("https://maps.googleapis.com/maps/api/directions/json?origin=" + googleMapsOrigin + "&" +
//                        "destination=" + googleMapsDestination + "&" +"key=" +directionsKeyAPI)
//                .build();
//
//        okHttpClient.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(@NotNull Call call, @NotNull IOException e) {
//                Log.e(TAG, "Threw exception");
//            }
//
//            @Override
//            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
//                final String myResponse = response.body().string();
//                Log.d(TAG, "myResponse" + myResponse);
//
//            }
//        });
    }


    private void getPolylinesFromResult(final DirectionsResult result) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                for(DirectionsRoute directionsRoute: result.routes) {
                    List<com.google.maps.model.LatLng> polylinePath = PolylineEncoding.decode(
                            directionsRoute.overviewPolyline.getEncodedPath());

                    List<LatLng> availablePaths = new ArrayList<>();

                    for(com.google.maps.model.LatLng coordinates: polylinePath) {
                        availablePaths.add(new LatLng(coordinates.lat, coordinates.lng));
                    }
                    Polyline polyline = mMap.addPolyline(new PolylineOptions().addAll(availablePaths));
                    polyline.setClickable(true);
                    polyline.setColor(R.color.colorAccent);

                    Log.d(TAG, "polyline is clickable?: " + polyline.isClickable());


                }
            }
        });
    }



    public class LocationBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("ACTION_LOCATION_DATA")) {
                latitude = intent.getDoubleExtra("latitude", 0f);
                longitude = intent.getDoubleExtra("longitude", 0f);

                Log.d(TAG, "BroadcastReceiver latitude: " + latitude);
                Log.d(TAG, "BroadcastReceiver longitude: " + longitude);

                //Saving user location
                if (mUserLocation != null) {

                    DocumentReference userDocumentReference =
                            firebaseFirestore.collection("User")
                                    .document(FirebaseAuth.getInstance().getUid());

                    userDocumentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Log.d(TAG, "I was able to retrieve the current user!");

                            User currentUser = documentSnapshot.toObject(User.class);
                            mUserLocation.setUser(currentUser);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "I was unable to find the current user :o( ");
                            e.printStackTrace();
                            e.getLocalizedMessage();
                        }
                    });

                    GeoPoint geoPoint = new GeoPoint(latitude, longitude);
                    mUserLocation.setGeoPoint(geoPoint);
                    DocumentReference documentReference =
                            firebaseFirestore.collection("User Location")
                                    .document(FirebaseAuth.getInstance().getUid());

                    documentReference.set(mUserLocation).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Log.d(TAG, "I think I just successfully entered the user location to the database");
                            Log.d(TAG, "mUserLocation geopoint lat: " + mUserLocation.getGeoPoint().getLatitude());
                            Log.d(TAG, "mUserLocation geopoint long: " + mUserLocation.getGeoPoint().getLongitude());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "Enter location into Firestore failed miserably");
                        }
                    });
                }
            }
        }
    }
}