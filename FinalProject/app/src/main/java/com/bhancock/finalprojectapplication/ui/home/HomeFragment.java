package com.bhancock.finalprojectapplication.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bhancock.finalprojectapplication.R;
import com.bhancock.finalprojectapplication.adapter.HomeFeedListAdapter;
import com.bhancock.finalprojectapplication.adapter.PackageTabAdapter;
import com.google.android.material.tabs.TabLayout;

public class HomeFragment extends Fragment {

    private static final String TAG = HomeFragment.class.getSimpleName();

    private HomeViewModel homeViewModel;
    private RecyclerView mRecyclerView;
    private HomeFeedListAdapter mAdapter;
    private ProgressBar mProgressBar;
    private Context mContext;

    private Context context;
    private Toolbar toolbar;
    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    private PackageTabAdapter adapter;

    private void initRecyclerView() {
        mContext = getActivity().getApplicationContext();
//        mAdapter = new HomeFeedListAdapter(getContext(), homeViewModel.getHomeFeedItems().getValue());
        mAdapter = new HomeFeedListAdapter(getContext(), homeViewModel.getVideoList().getValue());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setBackgroundColor(Color.BLACK);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
//        homeViewModel.init();
//
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        toolbar = (Toolbar) root.findViewById(R.id.toolbar);
        viewPager = (ViewPager) root.findViewById(R.id.viewPager);
        viewPager.setOffscreenPageLimit(2);
        tabLayout = (TabLayout) root.findViewById(R.id.packagetablayout);
        createTabFragment();

//        mRecyclerView = root.findViewById(R.id.recycler_view);
//        mProgressBar = root.findViewById(R.id.progress_bar);
//
//
//        initRecyclerView();
//
//        homeViewModel.getVideoList().observe(getViewLifecycleOwner(), new Observer<List<Video>>() {
//            @Override
//            public void onChanged(List<Video> videoList) {
//                mAdapter.notifyDataSetChanged();
//            }
//        });


//        homeViewModel.getHomeFeedItems().observe(getViewLifecycleOwner(), new Observer<ArrayList<HomeFeedItem>>() {
//            @Override
//            public void onChanged(ArrayList<HomeFeedItem> homeFeedItems) {
//                mAdapter.notifyDataSetChanged();
//            }
//        });
        return root;
    }

    private void createTabFragment() {
        adapter = new PackageTabAdapter(getChildFragmentManager(), tabLayout);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}