package com.bhancock.finalprojectapplication.model;

import com.google.firebase.database.ServerValue;

public class User {
    public String username;
    public String email;



    public String phone;
    private String user_id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
