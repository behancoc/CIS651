package com.bhancock.lab7application;

import com.google.firebase.database.ServerValue;

public class User {
    public String displayName;
    public String email;
    public String phone;
    public Object timestamp;

    public User() {

    }

    public User(String displayName, String email, String phone) {
        this.displayName = displayName;
        this.email = email;
        this.phone = phone;
        this.timestamp = ServerValue.TIMESTAMP;
    }

    public Object getTimestamp() {
        return timestamp;
    }
}
