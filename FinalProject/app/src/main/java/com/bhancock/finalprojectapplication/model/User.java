package com.bhancock.finalprojectapplication.model;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class User {
    public String email;
    public String userName;
    public String phone;
    private String userId;
    private @ServerTimestamp Date timestamp;

    public User() {
    }

    public User(String username, String email, String userId) {
        this.userName = username;
        this.email = email;
        this.userId = userId;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
