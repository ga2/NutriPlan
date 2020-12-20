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
import com.cafape.nutriplan.fragments.Fragment_PatientAccount_File;
import com.cafape.nutriplan.fragments.Fragment_PatientAccount_Notes;
import com.cafape.nutriplan.fragments.Fragment_PatientAccount_Report;
import com.cafape.nutriplan.fragments.Fragment_PatientAccount_Visits;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter_patientaccount extends FragmentPagerAdapter
{

    final int NUMBER_PAGES = 4;

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.activityappointments_string_notes, R.string.activityappointments_string_visits,
    R.string.activityappointments_string_file, R.string.activityappointments_string_report};
    private final Context mContext;

    public SectionsPagerAdapter_patientaccount(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0: { return Fragment_PatientAccount_Notes.newInstance("0", "page 1");}
            case 1: {return Fragment_PatientAccount_Visits.newInstance("1", "page 2");}
            case 2: {return Fragment_PatientAccount_File.newInstance("2", "page 3");}
            case 3: {return Fragment_PatientAccount_Report.newInstance("3", "page 4");}
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