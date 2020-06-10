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
        //TODO: Query database or Firebase or Room... not really sure where I am going to store favs

        //Dummy data retrieved from Firebase
        setUserPlaces();

        MutableLiveData<List<VisitedPlaces>> data = new MutableLiveData<>();
        data.setValue(dataset);
        return data;
    }

    private void setUserPlaces() {
        //TODO: Set places that the user favorites here...
        dataset.add(
                new VisitedPlaces("https://i.redd.it/tpsnoz5bzo501.jpg",
                        "Trondheim")
        );
        dataset.add(
                new VisitedPlaces("https://i.redd.it/qn7f9oqu7o501.jpg",
                        "Portugal")
        );
        dataset.add(
                new VisitedPlaces("https://i.redd.it/j6myfqglup501.jpg",
                        "Rocky Mountain National Park")
        );
        dataset.add(
                new VisitedPlaces("https://i.redd.it/0h2gm1ix6p501.jpg",
                        "Havasu Falls")
        );
        dataset.add(
                new VisitedPlaces("https://i.redd.it/k98uzl68eh501.jpg",
                        "Mahahual")
        );
        dataset.add(
                new VisitedPlaces("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg",
                        "Frozen Lake")
        );
        dataset.add(
                new VisitedPlaces("https://i.redd.it/obx4zydshg601.jpg",
                        "Austrailia")
        );
    }
}
