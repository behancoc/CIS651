package com.bhancock.finalprojectapplication.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bhancock.finalprojectapplication.ui.home.DomesticFragment;
import com.bhancock.finalprojectapplication.ui.home.InternationalFragment;
import com.google.android.material.tabs.TabLayout;

public class PackageTabAdapter extends FragmentStatePagerAdapter {

    TabLayout tabLayout;

    public PackageTabAdapter(FragmentManager fragmentManager, TabLayout tabLayout) {
        super(fragmentManager);
        this.tabLayout = tabLayout;
    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position == 0) {
            fragment = new DomesticFragment();
        }
        else if(position == 1) {
            fragment = new InternationalFragment();
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
