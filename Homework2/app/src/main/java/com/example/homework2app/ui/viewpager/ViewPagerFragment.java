package com.example.homework2app.ui.viewpager;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.example.homework2app.MovieData;
import com.example.homework2app.MyRecyclerAdapter;
import com.example.homework2app.R;
import com.example.homework2app.ui.about.AboutMeViewModel;


public class ViewPagerFragment extends Fragment {

    ViewPager2 viewPager2;
    private MovieData movieData = new MovieData();
    private final MyRecyclerAdapter myRecyclerAdapter = new MyRecyclerAdapter(movieData.getMoviesList());
    MovieCardFragment movieCardFragment = new MovieCardFragment();
    //private final ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(movieCardFragment);

    public ViewPagerFragment() {

    }

    public static ViewPagerFragment newInstance(Integer counter) {
        ViewPagerFragment viewPagerFragment = new ViewPagerFragment();
        return viewPagerFragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);
        viewPager2 = view.findViewById(R.id.view_pager2);
        //=iewPager2.setAdapter(viewPagerAdapter);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
