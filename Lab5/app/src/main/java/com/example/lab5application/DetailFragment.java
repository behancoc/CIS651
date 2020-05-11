package com.example.lab5application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

public class DetailFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.detail_fragment, container, false);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.img_poster);
        TextView title = (TextView) rootView.findViewById(R.id.title);
        TextView year = (TextView) rootView.findViewById(R.id.year);
        Bundle args = getArguments();
        imageView.setImageResource(args.getInt("img_id"));
        title.setText(args.getString("mtitle"));
        ViewCompat.setTransitionName(imageView, args.getString("mtitle"));
        year.setText(args.getString("myear"));
        return rootView;
    }
}
