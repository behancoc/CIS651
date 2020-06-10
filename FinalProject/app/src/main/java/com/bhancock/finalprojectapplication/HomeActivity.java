package com.bhancock.finalprojectapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityManagerCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = HomeActivity.class.getSimpleName();
    public static final int REQUEST_CODE_PERMISSIONS = 101;
    private FusedLocationProviderClient mFusedLocationClient;
    protected Location mLastLocation;


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
        requestLocationPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume()");
        stopLocationService();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
        stopLocationService();
    }

    private void requestLocationPermission() {

        boolean foreground = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if (foreground) {
            boolean background = ActivityCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;

            if (background) {
                //handleLocationUpdates();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, REQUEST_CODE_PERMISSIONS);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION}, REQUEST_CODE_PERMISSIONS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSIONS) {

            boolean foreground = false, background = false;

            for (int i = 0; i < permissions.length; i++) {
                if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    //foreground permission allowed
                    if (grantResults[i] >= 0) {
                        foreground = true;
                        Toast.makeText(getApplicationContext(), "Foreground location permission allowed", Toast.LENGTH_SHORT).show();
                        continue;
                    } else {
                        Toast.makeText(getApplicationContext(), "Location Permission denied", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }

                if (permissions[i].equalsIgnoreCase(Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                    if (grantResults[i] >= 0) {
                        foreground = true;
                        background = true;
                        Toast.makeText(getApplicationContext(), "Background location location permission allowed", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Background location location permission denied", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            if (foreground) {
                if (background) {
                    handleLocationUpdates();
                } else {
                    handleForegroundLocationUpdates();
                }
            }
        }
    }

    private void handleLocationUpdates() {
        //foreground and background
        Toast.makeText(getApplicationContext(),"Start Foreground and Background Location Updates",Toast.LENGTH_SHORT).show();
    }

    private void handleForegroundLocationUpdates() {
        //handleForeground Location Updates
        Toast.makeText(getApplicationContext(),"Start foreground location updates",Toast.LENGTH_SHORT).show();
        startLocationService();
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

}