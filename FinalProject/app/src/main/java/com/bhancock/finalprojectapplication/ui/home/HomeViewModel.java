package com.bhancock.finalprojectapplication.ui.home;

import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bhancock.finalprojectapplication.model.VisitedPlaces;
import com.bhancock.finalprojectapplication.repository.VisitedPlacesRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {

    private static final String TAG = HomeViewModel.class.getSimpleName();

    private MutableLiveData<String> mText;

    private MutableLiveData<List<VisitedPlaces>> mUserPlaces;


    private VisitedPlacesRepository mRepository;




    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is the home fragment");
    }


    public LiveData<String> getText() {
        return mText;
    }


}