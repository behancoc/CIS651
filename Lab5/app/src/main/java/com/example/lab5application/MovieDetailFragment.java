package com.example.lab5application;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MovieDetailFragment extends Fragment {

    public static MovieDetailFragment newInstance(int i , String t, String y, float r, String d) {
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        Bundle args= new Bundle();
        args.putInt("id", i);
        args.putString("title", t);
        args.putString("year", y);
        args.putFloat("rating", r);
        args.putString("description", d);
        movieDetailFragment.setArguments(args);
        return movieDetailFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();

        //Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_layout, container, false);
        ImageView imageView = view.findViewById(R.id.large_image);
        imageView.setImageResource(args.getInt("id"));
        EditText editText = view.findViewById(R.id.title_text);
        editText.setText(args.getString("title"));
        EditText yearText = view.findViewById(R.id.year_text);
        yearText.setText(args.getString("year"));
        RatingBar ratingBar = view.findViewById(R.id.movie_rating);
        ratingBar.setRating(args.getFloat("rating"));
        TextView descriptionText = view.findViewById(R.id.description);
        descriptionText.setText(args.getString("description"));

        return view;
    }
}
