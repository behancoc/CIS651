package com.bhancock.finalprojectapplication.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bhancock.finalprojectapplication.ui.home.UserFeedFragment;
import com.bhancock.finalprojectapplication.ui.home.FollowingFeedFragment;
import com.google.android.material.tabs.TabLayout;

public class TabLayoutAdapter extends FragmentStatePagerAdapter {

    TabLayout tabLayout;

    public TabLayoutAdapter(FragmentManager fragmentManager, TabLayout tabLayout) {
        super(fragmentManager);
        this.tabLayout = tabLayout;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new UserFeedFragment();
        }
        else if(position == 1) {
            fragment = new FollowingFeedFragment();
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = null;
        if(position == 0) {
            title = "YOU";
        } else if(position == 1) {
            title = "FOLLOWING";
        }

        return title;
    }
}
