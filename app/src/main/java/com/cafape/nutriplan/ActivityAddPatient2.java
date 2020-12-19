package com.cafape.nutriplan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.cafape.nutriplan.ui.main.SectionsPagerAdapter_addpatient;
import com.cafape.nutriplan.ui.main.SectionsPagerAdapter_calc;

public class ActivityAddPatient2 extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient2);

        SectionsPagerAdapter_addpatient sectionsPagerAdapterCalc = new SectionsPagerAdapter_addpatient(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapterCalc);
    }
}