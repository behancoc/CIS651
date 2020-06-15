package com.bhancock.finalprojectapplication.ui.explore;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bhancock.finalprojectapplication.model.VisitedPlaces;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class ExploreViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<LatLng> userCoordinates;
    private MutableLiveData<List<VisitedPlaces>> mUserPlaces;


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