package com.bhancock.finalprojectapplication.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bhancock.finalprojectapplication.R;
import com.bhancock.finalprojectapplication.adapter.HomeFeedListAdapter;
import com.bhancock.finalprojectapplication.adapter.VisitedPlacesAdapter;
import com.bhancock.finalprojectapplication.model.HomeFeedItem;
import com.bhancock.finalprojectapplication.model.Video;
import com.bhancock.finalprojectapplication.model.VisitedPlaces;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private HomeViewModel homeViewModel;
    private RecyclerView mRecyclerView;
    private HomeFeedListAdapter mAdapter;
    private ProgressBar mProgressBar;
    private Context mContext;

    private void initRecyclerView() {
        mContext = getActivity().getApplicationContext();
//        mAdapter = new HomeFeedListAdapter(getContext(), homeViewModel.getHomeFeedItems().getValue());
        mAdapter = new HomeFeedListAdapter(getContext(), homeViewModel.getVideoList().getValue());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setBackgroundColor(Color.BLACK);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.init();

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = root.findViewById(R.id.recycler_view);
        mProgressBar = root.findViewById(R.id.progress_bar);


        initRecyclerView();

        homeViewModel.getVideoList().observe(getViewLifecycleOwner(), new Observer<List<Video>>() {
            @Override
            public void onChanged(List<Video> videoList) {
                mAdapter.notifyDataSetChanged();
            }
        });


//        homeViewModel.getHomeFeedItems().observe(getViewLifecycleOwner(), new Observer<ArrayList<HomeFeedItem>>() {
//            @Override
//            public void onChanged(ArrayList<HomeFeedItem> homeFeedItems) {
//                mAdapter.notifyDataSetChanged();
//            }
//        });
        return root;
    }
}