package com.bhancock.finalprojectapplication.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.bhancock.finalprojectapplication.model.HomeFeedItem;
import com.bhancock.finalprojectapplication.model.Video;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VideoRepository {

    private static final String TAG = VideoRepository.class.getSimpleName();
    private static VideoRepository videoRepositoryInstance;
    //private ArrayList<HomeFeedItem> dataset = new ArrayList<HomeFeedItem>();
    private List<Video> dataset = new ArrayList<Video>();

    public static VideoRepository getInstance() {
        if (videoRepositoryInstance == null) {
            videoRepositoryInstance = new VideoRepository();
        }
        return videoRepositoryInstance;
    }

//    public MutableLiveData<ArrayList<HomeFeedItem>> getHomeFeedObjects() {
//        //TODO: Query database or Firebase or Room... not really sure where I am going to store favs
//        fetchJSON();
//
//
//        //Dummy data retrieved from Firebase
//        MutableLiveData<ArrayList<HomeFeedItem>> data = new MutableLiveData<>();
//
//
//        data.setValue(dataset);
//        return data;
//    }

    public MutableLiveData<List<Video>> getVideoObjects() {
        fetchJSON();
        MutableLiveData<List<Video>> data = new MutableLiveData<>();
        data.setValue(dataset);
        return data;
    }

    private void setVideo() {

    }

    public void fetchJSON() {
        Log.d(TAG, "Attempting to fetch JSON");

        String url = "https://api.letsbuildthatapp.com/youtube/home_feed";

        OkHttpClient client = new OkHttpClient();

        try {
            Request request = new Request.Builder().url(url).build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response)
                        throws IOException {
                    //Log.d(TAG, "Response body: " + response.body().string());

                    String responseString = response.body().string();

                    Log.d(TAG, "responseString" + responseString);

                    GsonBuilder builder = new GsonBuilder();
                    Gson gson = builder.create();

                    HomeFeedItem homeFeed = gson.fromJson(responseString, HomeFeedItem.class);

                    for(Video foundVideo : homeFeed.getVideos()) {
                        dataset.add(foundVideo);
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
