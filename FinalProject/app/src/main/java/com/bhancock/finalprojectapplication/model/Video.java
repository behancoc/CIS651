package com.bhancock.finalprojectapplication.model;

//id": 1,
//        "name": "Instagram Firebase - Learn How to Create Users, Follow, and Send Push Notifications",
//        "link": "https://www.letsbuildthatapp.com/course/instagram-firebase",
//        "imageUrl": "https://letsbuildthatapp-videos.s3-us-west-2.amazonaws.com/04782e30-d72a-4917-9d7a-c862226e0a93",
//        "numberOfViews": 20000,
//        "channel": {
//        "name": "Lets Build That App",
//        "profileImageUrl": "https://letsbuildthatapp-videos.s3-us-west-2.amazonaws.com/dda5bc77-327f-4944-8f51-ba4f3651ffdf",
//        "numberOfSubscribers": 100000

public class Video {
    public int id;
    public String name;
    public String link;
    public String imageUrl;
    public int numberOfViews;
    public Channel channel;

    public Video() {

   }

   public Video(int id, String name, String link, String imageUrl,
                int numberOfViews, Channel channel ) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.imageUrl = imageUrl;
        this.numberOfViews = numberOfViews;
        this.channel = channel;
   }

}

