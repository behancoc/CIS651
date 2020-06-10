package com.bhancock.finalprojectapplication.ui.profile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bhancock.finalprojectapplication.R;
import com.bhancock.finalprojectapplication.adapter.ProfileGridAdapter;
import com.bhancock.finalprojectapplication.adapter.VisitedPlacesAdapter;
import com.bhancock.finalprojectapplication.model.VisitedPlaces;

import java.util.List;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private RecyclerView mProfileGridRecyclerView;
    private ProfileGridAdapter mAdapter;
    private Context mContext;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    private void initRecyclerView() {
        mContext = getActivity().getApplicationContext();
        mAdapter = new ProfileGridAdapter(getContext(), profileViewModel.getUserPlaces().getValue());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext, 3);
        mProfileGridRecyclerView.setLayoutManager(layoutManager);
        mProfileGridRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        profileViewModel.init();
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        mProfileGridRecyclerView = root.findViewById(R.id.profile_grid_recycler_view);

        initRecyclerView();

        profileViewModel.getUserPlaces().observe(getViewLifecycleOwner(), new Observer<List<VisitedPlaces>>() {
            @Override
            public void onChanged(List<VisitedPlaces> visitedPlaces) {
                mAdapter.notifyDataSetChanged();
            }
        });

        return root;
    }
}