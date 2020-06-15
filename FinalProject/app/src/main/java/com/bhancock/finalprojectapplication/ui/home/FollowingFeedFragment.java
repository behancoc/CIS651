package com.bhancock.finalprojectapplication.ui.home;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhancock.finalprojectapplication.R;
import com.bhancock.finalprojectapplication.adapter.TripViewAdapter;
import com.bhancock.finalprojectapplication.model.Trip;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
/**
 * A simple {@link Fragment} subclass.
 */
public class FollowingFeedFragment extends Fragment {
    private Context context;
    private RecyclerView recyclerView;
    TripViewAdapter tripViewAdapter;
    private ArrayList<Trip> tripList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following_home_feed, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.context = getContext();
        //recyclerView = view.findViewById(R.id.recyclerView);
        //populateData();
        //tripViewAdapter = new TripViewAdapter(context, tripList);
        //recyclerView.setAdapter(tripViewAdapter);
    }

//    public Trip(String mapImage, String id, String title, String description, int likeCount) {
//        this.mapImage = mapImage;
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.likeCount = likeCount;
//    }

    ChildEventListener childEventListener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    private void populateData() {
//        tripList.add(new Trip("image_1", "Android", "California", "Los Angeles", 27));
//        tripList.add(new Trip("image_2",  "Android", "California", "Los Angeles", 28));
//        tripList.add(new Trip("image_3",  "Android", "California", "Los Angeles", 29));
//        tripList.add(new Trip("image_4",  "Android", "California", "Los Angeles", 30));
//        tripList.add(new Trip("image_5",  "Android", "California", "Los Angeles", 31));
//        tripList.add(new Trip("image_6",  "Android", "California", "Los Angeles", 32));
//        tripList.add(new Trip("image_1",  "Android", "California", "Los Angeles", 33));
//        tripList.add(new Trip("image_2",  "Android", "California", "Los Angeles", 24));
//        tripList.add(new Trip("image_3",  "Android", "California", "Los Angeles", 35));
//        tripList.add(new Trip("image_4",  "Android", "California", "Los Angeles", 36));
//        tripList.add(new Trip("image_5",  "Android", "California", "Los Angeles", 37));
//        tripList.add(new Trip("image_6",  "Android", "California", "Los Angeles", 38));
//        tripList.add(new Trip("image_1",  "Android", "California", "Los Angeles", 29));
    }
}