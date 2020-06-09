package com.bhancock.finalprojectapplication.ui.profile;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bhancock.finalprojectapplication.model.UserPreviousLocation;

import java.util.List;

public class ProfileViewModel extends ViewModel {

    private MutableLiveData<List<UserPreviousLocation>>  mAvailableMapViews;


    private MutableLiveData<String> mText;

    public ProfileViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the profile fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<UserPreviousLocation>> getAvailableMapViews() {
        return mAvailableMapViews;
    }
}