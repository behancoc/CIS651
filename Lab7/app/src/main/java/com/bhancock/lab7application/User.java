package com.bhancock.lab7application;

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
    }

    public Object getTimestamp() {
        return timestamp;
    }
}
