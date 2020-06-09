package com.bhancock.finalprojectapplication.model;

public class UserFeedItem {

    private String imageUrl;

    private UserFeedItem(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
