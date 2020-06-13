package com.bhancock.finalprojectapplication.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bhancock.finalprojectapplication.R;
import com.bhancock.finalprojectapplication.adapter.TabLayoutAdapter;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private HomeViewModel homeViewModel;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private Context mContext;

    private Context context;
    private Toolbar toolbar;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    private TabLayoutAdapter adapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        viewPager = (ViewPager) root.findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(2);
        tabLayout = (TabLayout) root.findViewById(R.id.tab_layout);
        createTabFragment();

        return root;
    }

    private void createTabFragment() {
        adapter = new TabLayoutAdapter(getChildFragmentManager(), tabLayout);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}