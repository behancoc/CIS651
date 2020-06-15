package com.bhancock.finalprojectapplication.ui.profile;

import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toolbar;

import com.bhancock.finalprojectapplication.EditProfileActivity;
import com.bhancock.finalprojectapplication.LoginActivity;
import com.bhancock.finalprojectapplication.R;
import com.bhancock.finalprojectapplication.adapter.ProfileGridAdapter;
import com.bhancock.finalprojectapplication.adapter.VisitedPlacesAdapter;
import com.bhancock.finalprojectapplication.model.VisitedPlaces;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private RecyclerView mProfileGridRecyclerView;
    private ProfileGridAdapter mAdapter;
    private Context mContext;
    private Button mEditProfileButton;
    private Toolbar mToolbar;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();


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

        setHasOptionsMenu(true);

        mEditProfileButton = root.findViewById(R.id.edit_profile_button);
        mEditProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), EditProfileActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    private void logoutUser() {
        mFirebaseAuth.signOut();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile_logout:
                logoutUser();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}