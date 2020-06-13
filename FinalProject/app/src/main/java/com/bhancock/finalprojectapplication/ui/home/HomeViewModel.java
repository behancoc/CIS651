package com.bhancock.finalprojectapplication.ui.home;

import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bhancock.finalprojectapplication.model.Video;
import com.bhancock.finalprojectapplication.model.VisitedPlaces;
import com.bhancock.finalprojectapplication.repository.VideoRepository;
import com.bhancock.finalprojectapplication.repository.VisitedPlacesRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private static final String TAG = HomeViewModel.class.getSimpleName();

    private MutableLiveData<String> mText;
    private MutableLiveData<ProgressBar> mProgressBar;
    private MutableLiveData<List<VisitedPlaces>> mUserPlaces;
    private MutableLiveData<List<Video>> mVideoList;
    private MutableLiveData<List<Video>> mHomeFeedList;
    private VisitedPlacesRepository mRepository;
    private VideoRepository mVideoRepository;



    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the home fragment");
    }

    public void init() {
        if (mVideoList != null) {
            //I've already retrieved the data
            return;
        }
        mVideoRepository = VideoRepository.getInstance();
       //mHomeFeedList = mVideoRepository.getVideoObjects();
        mVideoList = mVideoRepository.getVideoObjects();

    }

    public LiveData<String> getText() {
        return mText;
    }


    public LiveData<List<Video>> getVideoList() {
        return mVideoList;
    }
}