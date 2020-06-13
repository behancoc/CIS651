package com.bhancock.finalprojectapplication.ui.home;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhancock.finalprojectapplication.R;
import com.bhancock.finalprojectapplication.adapter.TripViewAdapter;
import com.bhancock.finalprojectapplication.model.Trip;

import java.util.ArrayList;

public class UserFeedFragment extends Fragment {
    private Context mContext;
    private RecyclerView mRecyclerView;
    private TripViewAdapter mTripViewAdapter;
    private ArrayList<Trip> tripList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user_home_feed, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.mContext = getContext();
        mRecyclerView = view.findViewById(R.id.recyclerView);
        populateData();
        mTripViewAdapter = new TripViewAdapter(mContext, tripList);
        mRecyclerView.setAdapter(mTripViewAdapter);

    }
    private void populateData() {
        tripList.add(new Trip("image_1", "Android", "California", "Los Angeles", 27));
        tripList.add(new Trip("image_2",  "Android", "California", "Los Angeles", 28));
        tripList.add(new Trip("image_3",  "Android", "California", "Los Angeles", 29));
        tripList.add(new Trip("image_4",  "Android", "California", "Los Angeles", 30));
        tripList.add(new Trip("image_5",  "Android", "California", "Los Angeles", 31));
        tripList.add(new Trip("image_6",  "Android", "California", "Los Angeles", 32));
        tripList.add(new Trip("image_1",  "Android", "California", "Los Angeles", 33));
        tripList.add(new Trip("image_2",  "Android", "California", "Los Angeles", 24));
        tripList.add(new Trip("image_3",  "Android", "California", "Los Angeles", 35));
        tripList.add(new Trip("image_4",  "Android", "California", "Los Angeles", 36));
        tripList.add(new Trip("image_5",  "Android", "California", "Los Angeles", 37));
        tripList.add(new Trip("image_6",  "Android", "California", "Los Angeles", 38));
        tripList.add(new Trip("image_1",  "Android", "California", "Los Angeles", 29));
    }
}
