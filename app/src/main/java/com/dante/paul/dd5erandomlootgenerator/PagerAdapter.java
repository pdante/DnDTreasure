package com.dante.paul.dd5erandomlootgenerator;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.dante.paul.dd5erandomlootgenerator.Fragments.ItemsFragment;
import com.dante.paul.dd5erandomlootgenerator.Fragments.SpellsFragment;
import com.dante.paul.dd5erandomlootgenerator.Fragments.TrackerFragment;
import com.dante.paul.dd5erandomlootgenerator.Fragments.TreasureFragment;

public class PagerAdapter extends FragmentStatePagerAdapter {
    private final int mNumOfTabs;

    public PagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return new TreasureFragment();
            case 1: return new ItemsFragment();
            case 2: return new SpellsFragment();
            case 3: return new TrackerFragment();
            default: return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
