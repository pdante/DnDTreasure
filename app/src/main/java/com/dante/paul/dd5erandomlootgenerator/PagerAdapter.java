package com.dante.paul.dd5erandomlootgenerator;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dante.paul.dd5erandomlootgenerator.Fragments.ItemsFragment;
import com.dante.paul.dd5erandomlootgenerator.Fragments.SpellsFragment;
import com.dante.paul.dd5erandomlootgenerator.Fragments.TrackerFragment;
import com.dante.paul.dd5erandomlootgenerator.Fragments.TreasureFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private final boolean includeTracker;

    public PagerAdapter(FragmentManager fm, boolean includeTracker) {
        super(fm);
        this.includeTracker = includeTracker;
    }

    @Override
    public Fragment getItem(int position) {
        if (includeTracker) {
            switch (position) {
                case 0: return new TreasureFragment();
                case 1: return new TrackerFragment();
                case 2: return new ItemsFragment();
                case 3: return new SpellsFragment();
                default: return null;
            }
        }
        switch (position) {
            case 0: return new TreasureFragment();
            case 1: return new ItemsFragment();
            case 2: return new SpellsFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return includeTracker ? 4 : 3;
    }
}
