package com.bhancock.finalprojectapplication.model;

import android.os.Parcelable;

import android.os.Parcel;

public class Trip implements Parcelable {
    private String mapImage;
    private String id;
    private String title;
    private String description;
    private int likeCount;
    private String external_link;

    public Trip(String mapImage, String id, String title, String description, int likeCount) {
        this.mapImage = mapImage;
        this.id = id;
        this.title = title;
        this.description = description;
        this.likeCount = likeCount;
    }

    public Trip(String mapImage, String title, String description) {
        this.mapImage = mapImage;
        this.title = title;
        this.description = description;
        }

        public Trip(String mapImage, String title, String description, String external_link) {
            this.mapImage = mapImage;
            this.title = title;
            this.description = description;
            this.external_link = external_link;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel out, int flags) {
            out.writeString(mapImage);
            out.writeString(title);
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
            mapImage = in.readString();
            title = in.readString();
            description = in.readString();
            external_link = in.readString();
        }
        public String getMapImage() {
            return mapImage;
        }
        public void setMapImage(String mapImage) {
            this.mapImage = mapImage;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
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

