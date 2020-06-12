package com.bhancock.finalprojectapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Collections;

import static com.bhancock.finalprojectapplication.Constants.GOOGLE_SERVICES_DIALOG;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    public static final int REQUEST_CODE_PERMISSIONS = 101;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;
    private LocationRequest mLocationRequest;

    private boolean mLocationPermissionGranted = false;

    Fragment mMyFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_search, R.id.navigation_explore, R.id.navigation_home,
                R.id.navigation_favorites, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");


        startLocationService();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
        stopLocationService();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationService();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        stopLocationService();
    }

    private boolean isLocationServiceCurrentlyActive() {
        ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        boolean locationServiceActive = false;

        if (activityManager == null) {
            locationServiceActive = false;
        }

        if (activityManager != null) {
            for (ActivityManager.RunningServiceInfo runningServiceInfo:
                    activityManager.getRunningServices(Integer.MAX_VALUE)) {
                if(LocationService.class.getName().equals(runningServiceInfo.service.getClassName())) {
                    locationServiceActive = true;
                }
            }
        }
        return  locationServiceActive;
    }

    private void startLocationService() {
        if(!isLocationServiceCurrentlyActive()) {
            Log.d(TAG, "Is service active?: " + isLocationServiceCurrentlyActive());
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(getApplicationContext(),"Starting Location Service", Toast.LENGTH_SHORT).show();
        }
    }

    private void stopLocationService() {
        if(isLocationServiceCurrentlyActive()) {
            Intent intent = new Intent(getApplicationContext(), LocationService.class);
            intent.setAction(Constants.ACTION_STOP_LOCATION_SERVICE);
            stopService(intent);
            Toast.makeText(getApplicationContext(), "Stopping Location Service", Toast.LENGTH_SHORT).show();

        }
    }

//    private void buildAlertMessageNoGps() {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
//                .setCancelable(false)
//                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
//                        Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                        startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
//                    }
//                });
//        final AlertDialog alert = builder.create();
//        alert.show();
//    }

    public boolean isGooglePlayServicesEnabled() {
        int googlePlayServicesAvailable = GoogleApiAvailability.getInstance()
                .isGooglePlayServicesAvailable(getApplicationContext());

        if(googlePlayServicesAvailable == ConnectionResult.SUCCESS){
            //everything is fine and the user can make map requests
            Log.d(TAG, "Google Play Services installed on device!");
            return true;
        }
        else if(GoogleApiAvailability.getInstance()
                .isUserResolvableError(googlePlayServicesAvailable)){

            Log.d(TAG, "Google Play Services not recognized on your device: ");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(
                    getParent(), googlePlayServicesAvailable, GOOGLE_SERVICES_DIALOG);
            dialog.show();
        }else{
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean isUseLocationEnabledOnDevice() {
        boolean isUseLocationEnabledOnDevice = false;


        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addAllLocationRequests(Collections.singleton(mLocationRequest));

        LocationServices.getSettingsClient(getApplication()).checkLocationSettings(builder.build());


        return isUseLocationEnabledOnDevice;
    }

}