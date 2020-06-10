package com.bhancock.finalprojectapplication.model;

import java.util.List;

public class HomeFeedItem {

    private String imageUrl;
    private List<Video> videos;

    public HomeFeedItem(List<Video> videos) {
        this.videos = videos;
    }

    public List<Video> getVideos() {
        return videos;
    }
}
