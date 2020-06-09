package com.bhancock.finalprojectapplication.model;

import android.location.Location;
import android.widget.ImageView;

import com.google.android.gms.maps.MapView;

public class UserPreviousLocation {

    private Double latitude;
    private Double longitude;
    private MapView mMapView;
    private ImageView mapImage;
    private String locationName;

    public UserPreviousLocation(Double latitude, Double longitude, ImageView mapImage ) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.mapImage = mapImage;
    }

    public UserPreviousLocation(Double latitude, Double longitude, MapView mapView ) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.mMapView = mapView;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }
}
