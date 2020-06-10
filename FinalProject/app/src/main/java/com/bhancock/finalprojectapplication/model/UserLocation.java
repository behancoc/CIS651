package com.bhancock.finalprojectapplication.model;


import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

public class UserLocation {
    private GeoPoint geoPoint;
    private @ServerTimestamp String serverTimestamp;
    private User user;

    public UserLocation() {

    }

    public UserLocation(GeoPoint geoPoint, String serverTimestamp, User user) {
        this.geoPoint = geoPoint;
        this.serverTimestamp = serverTimestamp;
        this.user = user;
    }


}
