package com.bhancock.finalprojectapplication.model;

public class Channel {
    private String name;
    private String profileImageUrl;
    private int numberOfSubscribers;

    public Channel() {

    }

    public  Channel(String name, String profileImageUrl, int numberOfSubscribers) {
        this.name = name;
        this.profileImageUrl = profileImageUrl;
        this.numberOfSubscribers = numberOfSubscribers;
    }

}
