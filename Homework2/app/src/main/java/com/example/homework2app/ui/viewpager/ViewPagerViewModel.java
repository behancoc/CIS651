package com.example.homework2app.ui.viewpager;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import com.example.homework2app.R;
import com.example.homework2app.ui.about.AboutMeViewModel;

public class ViewPagerViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    private MutableLiveData<Integer> mImageView;

    public ViewPagerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("ViewPager Fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
