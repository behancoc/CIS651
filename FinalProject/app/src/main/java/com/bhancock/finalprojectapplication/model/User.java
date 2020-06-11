package com.bhancock.finalprojectapplication.model;

import com.google.firebase.database.ServerValue;

public class User {
    public String username;
    public String email;
    public String phone;
    public Object timestamp;

    public User() {

    }

    public User(String username, String email, String phone) {
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.timestamp = ServerValue.TIMESTAMP;
    }

    public Object getTimestamp() {
        return timestamp;
    }

}
