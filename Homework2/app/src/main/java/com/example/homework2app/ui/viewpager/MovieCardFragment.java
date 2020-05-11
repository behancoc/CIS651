package com.example.homework2app.ui.viewpager;

import androidx.fragment.app.Fragment;

public class MovieCardFragment extends Fragment {
    public MovieCardFragment() {

    }

    public static MovieCardFragment newInstance (Integer counter) {
        MovieCardFragment movieCardFragment = new MovieCardFragment();
        return movieCardFragment;
    }

}
