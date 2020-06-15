package com.bhancock.finalprojectapplication.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bhancock.finalprojectapplication.model.VisitedPlaces;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private static final String TAG = HomeViewModel.class.getSimpleName();

    private MutableLiveData<String> mText;

    private MutableLiveData<List<VisitedPlaces>> mUserPlaces;


    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the home fragment");
    }


    public LiveData<String> getText() {
        return mText;
    }


}