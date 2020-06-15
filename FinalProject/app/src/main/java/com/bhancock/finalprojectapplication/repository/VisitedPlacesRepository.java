package com.bhancock.finalprojectapplication.repository;

import androidx.lifecycle.MutableLiveData;

import com.bhancock.finalprojectapplication.model.VisitedPlaces;

import java.util.ArrayList;
import java.util.List;

/**
 * Only want one instance that connects to web service
 */

public class VisitedPlacesRepository {

    private static VisitedPlacesRepository visitedPlacesRepositoryInstance;
    private ArrayList<VisitedPlaces> dataset = new ArrayList<>();

    public static VisitedPlacesRepository getInstance() {
        if (visitedPlacesRepositoryInstance == null) {
            visitedPlacesRepositoryInstance = new VisitedPlacesRepository();
        }
        return visitedPlacesRepositoryInstance;
    }

    public MutableLiveData<List<VisitedPlaces>> getUserPlaces() {
        //TODO: Query database or Firebase or Room... not really sure where I am going to store favs


        MutableLiveData<List<VisitedPlaces>> data = new MutableLiveData<>();
        data.setValue(dataset);
        return data;
    }

}
