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

public class Fragment1 extends Fragment {
    private FragmentTracker fragmentTracker;
    private View view;
    private static final String fragmentTitle = "Personal Infd";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentTracker.fragmentVisible(fragmentTitle);
        view = inflater.inflate(R.layout.fragment_first, container, false);
//        Button button_next = view.findViewById(R.id.next_button);
//        button_next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                fragmentTracker.goNext();
//            }
//        });

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
        EditText firstName = view.findViewById(R.id.first_name);
        EditText lastName = view.findViewById(R.id.last_name);
        fragmentTracker.saveNameAndLastName(
                firstName.getText().toString(),
                lastName.getText().toString());

        view = null;
    }
}
