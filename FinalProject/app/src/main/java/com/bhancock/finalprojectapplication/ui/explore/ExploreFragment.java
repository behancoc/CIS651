package com.bhancock.finalprojectapplication.ui.explore;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.CountDownTimer;
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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bhancock.finalprojectapplication.PermissionUtils;
import com.bhancock.finalprojectapplication.R;
import com.bhancock.finalprojectapplication.model.User;
import com.bhancock.finalprojectapplication.model.UserLocation;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;
import java.util.Timer;

public class ExploreFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener,
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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate() called....");

        firebaseFirestore = FirebaseFirestore.getInstance();
        mUserLocation = new UserLocation();
        userInteractingWithMap = false;


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

//        exploreViewModel.getCurrentLocation().observe(getViewLifecycleOwner(), new Observer<LatLng>() {
//            @Override
//            public void onChanged(LatLng latLng) {
//                Log.d(TAG, "Observing a new latitude and longitude!");
//            }
//        });

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        mapFragment.getMapAsync(this);
        isMapReady = true;


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

        //updateCameraToLastKnownPosition();

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

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(-33.87365, 151.20689), 10));

        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(getContext(), "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }


    @SuppressLint("MissingPermission")
    private void updateCameraToLastKnownPosition() {

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
    }

    private void updateCameraToFollowUserLocation(final double latitude, final double longitude) {
        if (!userInteractingWithMap) {
            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(latitude, longitude))
                    .zoom(18).bearing(0).tilt(70).build();
            mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }
    }

    @Override
    public void onCameraIdle() {
        new CountDownTimer(10000, 1000) {
            public void onTick(long millisUntilFinished) {

            }
            public void onFinish() {
                userInteractingWithMap = false;
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
            userInteractingWithMap = false;
            Log.d(TAG, "userInteractingWithMap is: " + false);
        }
    }

    public class LocationBroadCastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals("ACTION_LOCATION_DATA")) {
                latitude = intent.getDoubleExtra("latitude", 0f);
                longitude = intent.getDoubleExtra("longitude", 0f);

                Log.d(TAG, "BroadcastReceiver latitude: " + latitude);
                Log.d(TAG, "BroadcastReceiver longitude: " + longitude);

                updateCameraToFollowUserLocation(latitude, longitude);


                GeoPoint geoPoint = new GeoPoint(latitude, longitude);
                mUserLocation.setGeoPoint(geoPoint);

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

                    DocumentReference documentReference =
                            firebaseFirestore.collection("User Location")
                                    .document(FirebaseAuth.getInstance().getUid());

                    documentReference.set(mUserLocation).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "I think I just successfully entered the user location to the database");
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