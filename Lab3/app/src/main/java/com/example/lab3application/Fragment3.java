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

public class Fragment3 extends Fragment {
    private FragmentTracker fragmentTracker;
    private View view;
    private static final String fragmentTitle = "Personal Info";

    private static final String TAG = Fragment3.class.getSimpleName();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        fragmentTracker.fragmentVisible(fragmentTitle);
        view = inflater.inflate(R.layout.fragment_third, container, false);
        Button button_finished = view.findViewById(R.id.finished_button);
        button_finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText language = view.findViewById(R.id.language);
                fragmentTracker.saveLanguage(language.getText().toString());
                fragmentTracker.finished();
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
        view = null;
    }
}
