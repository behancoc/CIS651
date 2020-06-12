package com.bhancock.finalprojectapplication.model;


import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class UserLocation {
    private GeoPoint geoPoint;
    private @ServerTimestamp Date serverTimestamp;
    private User user;

    public UserLocation() {

    }

    public UserLocation(GeoPoint geoPoint, Date serverTimestamp, User user) {
        this.geoPoint = geoPoint;
        this.serverTimestamp = serverTimestamp;
        this.user = user;
    }

    public GeoPoint getGeoPoint() {
        return geoPoint;
    }

    public void setGeoPoint(GeoPoint geoPoint) {
        this.geoPoint = geoPoint;
    }

    public Date getServerTimestamp() {
        return serverTimestamp;
    }

    public void setServerTimestamp(Date serverTimestamp) {
        this.serverTimestamp = serverTimestamp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
