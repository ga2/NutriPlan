package com.cafape.nutriplan.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cafape.nutriplan.R;
import com.cafape.nutriplan.fragments.Fragment_Calc_BodyAdiposityIndex;
import com.cafape.nutriplan.fragments.Fragment_Calc_BodyMassIndex;
import com.cafape.nutriplan.fragments.Fragment_Calc_IdealWeight;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter_calc extends FragmentPagerAdapter
{
    final int NUMBER_PAGES = 3;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.activitycalc_string_tab_pi, R.string.activitycalc_string_tab_bmi, R.string.activitycalc_string_tab_bai};
    private final Context mContext;

    public SectionsPagerAdapter_calc(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0: { return Fragment_Calc_IdealWeight.newInstance("0", "page 1");}
            case 1: {return Fragment_Calc_BodyMassIndex.newInstance("1", "page 2");}
            case 2: {return Fragment_Calc_BodyAdiposityIndex.newInstance("2", "page 3");}
        }
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return NUMBER_PAGES;
    }
}