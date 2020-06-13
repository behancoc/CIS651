package com.bhancock.finalprojectapplication.model;

import java.util.List;

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

    public static class HomeFeedItem {

        private String imageUrl;
        private List<Video> videos;

        public HomeFeedItem(List<Video> videos) {
            this.videos = videos;
        }

        public List<Video> getVideos() {
            return videos;
        }
    }
}
