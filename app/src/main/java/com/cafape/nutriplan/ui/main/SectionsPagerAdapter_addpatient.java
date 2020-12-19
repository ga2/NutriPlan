package com.cafape.nutriplan.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.cafape.nutriplan.R;
import com.cafape.nutriplan.fragments.Fragment_AddPatient_Anamnesis;
import com.cafape.nutriplan.fragments.Fragment_AddPatient_Antropometry;
import com.cafape.nutriplan.fragments.Fragment_AddPatient_Survey;
import com.cafape.nutriplan.fragments.Fragment_Calc_BodyAdiposityIndex;
import com.cafape.nutriplan.fragments.Fragment_Calc_BodyMassIndex;
import com.cafape.nutriplan.fragments.Fragment_Calc_IdealWeight;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter_addpatient extends FragmentPagerAdapter
{
    final int NUMBER_PAGES = 3;

    private final Context mContext;

    public SectionsPagerAdapter_addpatient(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0: {return Fragment_AddPatient_Survey.newInstance("0", "page 1");}
            case 1: {return Fragment_AddPatient_Anamnesis.newInstance("1", "page 2");}
            case 2: {return Fragment_AddPatient_Antropometry.newInstance("2", "page 3");}
        }
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Override
    public int getCount() {
        return NUMBER_PAGES;
    }
}