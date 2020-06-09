package com.bhancock.finalprojectapplication.ui.favorites;

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

public class FavoritesFragment extends Fragment {

    private FavoritesViewModel favoritesViewModel;
    private RecyclerView mFavoritesRecyclerView;
    private VisitedPlacesAdapter mAdapter;
    private ProgressBar mProgressBar;
    private Context mContext;

    private void initRecyclerView() {
        mContext = getActivity().getApplicationContext();
        mAdapter = new VisitedPlacesAdapter(getContext(), favoritesViewModel.getUserPlaces().getValue());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mFavoritesRecyclerView.setLayoutManager(layoutManager);
        mFavoritesRecyclerView.setAdapter(mAdapter);

    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        favoritesViewModel = new ViewModelProvider(this).get(FavoritesViewModel.class);
        favoritesViewModel.init();
        View root = inflater.inflate(R.layout.fragment_favorites, container, false);
        mFavoritesRecyclerView = root.findViewById(R.id.favorites_recycler_view);
        mProgressBar = root.findViewById(R.id.progress_bar);


        initRecyclerView();

        favoritesViewModel.getUserPlaces().observe(getViewLifecycleOwner(), new Observer<List<VisitedPlaces>>() {
            @Override
            public void onChanged(List<VisitedPlaces> placeVisiteds) {
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