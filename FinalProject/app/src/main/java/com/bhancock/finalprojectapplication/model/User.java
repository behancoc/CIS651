package com.bhancock.finalprojectapplication.model;

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
    }

    public Object getTimestamp() {
        return timestamp;
    }

}
