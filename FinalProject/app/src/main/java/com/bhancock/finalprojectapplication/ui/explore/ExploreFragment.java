package com.bhancock.finalprojectapplication.ui.explore;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
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

import com.bhancock.finalprojectapplication.Constants;
import com.bhancock.finalprojectapplication.LocationService;
import com.bhancock.finalprojectapplication.PermissionUtils;
import com.bhancock.finalprojectapplication.R;
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
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.OnSuccessListener;

public class ExploreFragment extends Fragment implements OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate() called....");

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

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        mapFragment.getMapAsync(this);


        return root;
    }

    @Override
    public void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();


    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();

    }

    @Override
    public void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setBuildingsEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        enableMyLocation();
        startLocationUpdates();
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
    private void startLocationUpdates() {

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
}