package com.example.bakingtime.details.steps;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by rezagama on 9/4/17.
 */

public class StepViewPagerAdapter extends FragmentPagerAdapter {
    private FragmentManager fragmentManager;
    private ArrayList<String> tabTitles;
    private ArrayList<Fragment> fragments;

    public StepViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
        this.fragmentManager = fragmentManager;
        tabTitles = new ArrayList<>();
        fragments = new ArrayList<>();
    }

    public void addPage(Fragment fragment, String title) {
        tabTitles.add(title);
        fragments.add(fragment);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
