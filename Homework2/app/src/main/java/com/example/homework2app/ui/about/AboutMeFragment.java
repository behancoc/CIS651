package com.example.homework2app.ui.about;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.homework2app.R;

public class AboutMeFragment extends Fragment {

    private AboutMeViewModel aboutMeViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_about_me, container, false);
        final TextView textView = view.findViewById(R.id.about_me_header);
        final ImageView imageView = view.findViewById(R.id.imageView_about_me);

        aboutMeViewModel = ViewModelProviders.of(this).get(AboutMeViewModel.class);

        aboutMeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
                textView.setGravity(Gravity.CENTER);
            }
        });

        aboutMeViewModel.getImageView().observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                imageView.setImageResource(R.drawable.brandon_hancock_image);
            }
        });

        return view;
    }
}
