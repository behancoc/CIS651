package com.example.homework2app.ui.about;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.homework2app.R;

public class AboutMeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    private MutableLiveData<Integer> mImageView;

    public AboutMeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("My name is Brandon Hancock.\n " + "\n" +
                "I am a Software Developer living in San Francisco, CA. \n" +  "\n" +
                "My interests include Android Development, Unity, Augmented Reality & Virtual Reality.");

        mImageView = new MutableLiveData<Integer>();
        mImageView.setValue(R.drawable.brandon_hancock_image);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Integer> getImageView() {
        return mImageView;
    }
}