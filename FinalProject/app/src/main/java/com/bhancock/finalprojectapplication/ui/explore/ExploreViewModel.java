package com.bhancock.finalprojectapplication.ui.explore;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bhancock.finalprojectapplication.model.VisitedPlaces;
import com.bhancock.finalprojectapplication.repository.VisitedPlacesRepository;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class ExploreViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<LatLng> userCoordinates;
    private MutableLiveData<List<VisitedPlaces>> mUserPlaces;


    public void init() {


//        if (mUserLocation != null) {
//            Log.d(TAG, "mUserPlaces value is not null ");
//            //I've already retrieved the data
//            return;
//        }
//        Log.d(TAG, "mUserPlaces value is null ");
//        mRepository = VisitedPlacesRepository.getInstance();
//        mUserPlaces = mRepository.getUserPlaces();
    }

    public ExploreViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the explore fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<LatLng> getCurrentLocation() {
        return userCoordinates;
    }

}