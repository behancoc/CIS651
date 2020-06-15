package com.bhancock.finalprojectapplication.model;

import android.os.Parcelable;

import android.os.Parcel;

public class Trip implements Parcelable {
    private String tripImage;

    private String tripTitle;
    private String tripDetails;
    private String description;
    private int tripLikes;
    private String external_link;

    public Trip() {

    }

    public Trip(String tripImage, String tripTitle, String tripDetails, int tripLikes) {
        this.tripImage = tripImage;
        this.tripTitle = tripTitle;
        this.tripDetails = tripDetails;
        this.tripLikes = tripLikes;
    }


    @Override
    public int describeContents() {
            return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(tripImage);
        out.writeString(tripDetails);
        out.writeString(description);
        out.writeString(external_link);
    }

    public static final Parcelable.Creator<Trip> CREATOR = new Parcelable.Creator<Trip>() {
        public Trip createFromParcel(Parcel in) {
            return new Trip(in);
        }
        public Trip[] newArray(int size) {
                return new Trip[size];
            }

    };

    private Trip(Parcel in) {
        tripImage = in.readString();
        tripDetails = in.readString();
        description = in.readString();
        external_link = in.readString();
    }

    public String getTripImage() {
        return tripImage;
    }

    public void setTripImage(String tripImage) {
        this.tripImage = tripImage;
    }

    public String getTripDetails() {
        return tripDetails;
    }

    public String getTripTitle() {
        return tripTitle;
    }

    public void setTripTitle(String tripTitle) {
        this.tripTitle = tripTitle;
    }

    public int getTripLikes() {
        return tripLikes;
    }

    public void setTripLikes(int tripLikes) {
        this.tripLikes = tripLikes;
    }

    public void setTripDetails(String tripDetails) {
        this.tripDetails = tripDetails;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getExternal_link() {
        return external_link;
    }

    public void setExternal_link(String external_link) {
        this.external_link = external_link;
    }
}

