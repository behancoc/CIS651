package com.bhancock.finalprojectapplication.repository;

import android.media.Image;

import androidx.lifecycle.MutableLiveData;

import com.bhancock.finalprojectapplication.model.VisitedPlaces;

import java.util.ArrayList;
import java.util.List;

public class ProfileGridRepository {
    private static  ProfileGridRepository profileGridRepositoryInstance;
    private ArrayList<VisitedPlaces> dataset = new ArrayList<>();

    public static ProfileGridRepository getInstance() {
        if (profileGridRepositoryInstance == null) {
            profileGridRepositoryInstance = new ProfileGridRepository();
        }
        return profileGridRepositoryInstance;
    }

    public MutableLiveData<List<VisitedPlaces>> getUserPlaces() {

        MutableLiveData<List<VisitedPlaces>> data = new MutableLiveData<>();
        data.setValue(dataset);
        return data;
    }
}
