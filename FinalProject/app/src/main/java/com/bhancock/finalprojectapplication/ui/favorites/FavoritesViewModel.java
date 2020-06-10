package com.bhancock.finalprojectapplication.ui.favorites;

import android.util.Log;
import android.widget.ProgressBar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bhancock.finalprojectapplication.model.VisitedPlaces;
import com.bhancock.finalprojectapplication.repository.VisitedPlacesRepository;

import java.util.List;

public class FavoritesViewModel extends ViewModel {

    private static final String TAG = FavoritesViewModel.class.getSimpleName();

    private MutableLiveData<String> mText;
    private MutableLiveData<ProgressBar> mProgressBar;
    private MutableLiveData<List<VisitedPlaces>> mUserPlaces;
    private VisitedPlacesRepository mRepository;

//    public FavoritesViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is the favorites fragment");
//    }

    public void init() {


        if (mUserPlaces != null) {
            Log.d(TAG, "mUserPlaces value is not null ");
            //I've already retrieved the data
            return;
        }
        Log.d(TAG, "mUserPlaces value is null ");
        mRepository = VisitedPlacesRepository.getInstance();
        mUserPlaces = mRepository.getUserPlaces();
    }


    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<VisitedPlaces>> getUserPlaces() {
        return mUserPlaces;
    }
}