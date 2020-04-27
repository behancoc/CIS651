package com.example.lab3application;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {
    private FragmentTracker fragmentTracker;
    private View view;
    private static final String fragmentTitle = "Location Information";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentTracker.fragmentVisible(fragmentTitle);
        view = inflater.inflate(R.layout.fragment_second, container, false);
        Button button_next = view.findViewById(R.id.next_button);
        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTracker.goNext();
            }
        });

        Button button_previous = view.findViewById(R.id.prev_button);
        button_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentTracker.goBack();
            }
        });

        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            fragmentTracker = (FragmentTracker) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "must implement EventTrack");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        EditText city = view.findViewById(R.id.city_name);
        EditText zip = view.findViewById(R.id.zip_code);
        fragmentTracker.saveCityAndZip(
                city.getText().toString(),
                zip.getText().toString());

        view = null;
    }
}
