package com.bhancock.finalprojectapplication.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bhancock.finalprojectapplication.model.VisitedPlaces;
import com.bhancock.finalprojectapplication.repository.ProfileGridRepository;

import java.util.List;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<List<VisitedPlaces>> mUserPlaces;
    private ProfileGridRepository mRepository;
    private MutableLiveData<String> mText;

    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the profile fragment");
    }

    public void init() {
        if (mUserPlaces != null) {
            //I've already retrieved the data
            return;
        }
        mRepository = ProfileGridRepository.getInstance();
        mUserPlaces = mRepository.getUserPlaces();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<VisitedPlaces>> getUserPlaces() {
        return mUserPlaces;
    }
}