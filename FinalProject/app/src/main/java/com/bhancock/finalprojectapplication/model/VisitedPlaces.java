package com.bhancock.finalprojectapplication.model;

public class VisitedPlaces {

    private static final String TAG = VisitedPlaces.class.getSimpleName();

    private String title;
    private String imageUrl;

    public VisitedPlaces(String imageUrl, String title) {
        this.title = title;
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }



}
