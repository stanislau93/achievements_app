package com.example.achievements.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.achievements.fragments.FragmentTabCategories;
import com.example.achievements.fragments.FragmentTabConfig;
import com.example.achievements.fragments.FragmentTabQuests;

public class PagerAdapter extends FragmentStatePagerAdapter {

    private int numOfTabs;

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new FragmentTabQuests();
            case 1:
                return new FragmentTabCategories();
            case 2:
                return new FragmentTabConfig();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return this.numOfTabs;
    }
}
