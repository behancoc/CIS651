package com.bhancock.finalprojectapplication.ui.home;

import android.content.Context;
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
import com.bhancock.finalprojectapplication.adapter.VisitedPlacesAdapter;
import com.bhancock.finalprojectapplication.model.VisitedPlaces;

import java.util.List;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private HomeViewModel homeViewModel;
    private RecyclerView mRecyclerView;
    private VisitedPlacesAdapter mAdapter;
    private ProgressBar mProgressBar;
    private Context mContext;

    private void initRecyclerView() {
        mContext = getActivity().getApplicationContext();
        mAdapter = new VisitedPlacesAdapter(getContext(), homeViewModel.getUserPlaces().getValue());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.init();
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        mRecyclerView = root.findViewById(R.id.recycler_view);
        mProgressBar = root.findViewById(R.id.progress_bar);


        initRecyclerView();

        homeViewModel.getUserPlaces().observe(getViewLifecycleOwner(), new Observer<List<VisitedPlaces>>() {
            @Override
            public void onChanged(List<VisitedPlaces> visitedPlaces) {
                mAdapter.notifyDataSetChanged();
            }
        });


        return root;
    }

    private void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }
}