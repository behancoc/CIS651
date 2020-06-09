package com.bhancock.finalprojectapplication.ui.profile;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bhancock.finalprojectapplication.R;
import com.bhancock.finalprojectapplication.adapter.UserPreviousLocationsAdapter;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private RecyclerView mUserSavdTripJourneys;
    private UserPreviousLocationsAdapter mAdapter;
    private Context mContext;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    private void initRecyclerView() {
        mContext = getActivity().getApplicationContext();
        mAdapter = new UserPreviousLocationsAdapter(getContext(), profileViewModel.getAvailableMapViews().getValue());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        profileViewModel =
                ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        final TextView textView = root.findViewById(R.id.text_profile);
        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}