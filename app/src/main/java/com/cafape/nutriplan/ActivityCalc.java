package com.cafape.nutriplan;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.cafape.nutriplan.ui.main.SectionsPagerAdapter_calc;

public class ActivityCalc extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);

        SectionsPagerAdapter_calc sectionsPagerAdapterCalc = new SectionsPagerAdapter_calc(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapterCalc);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }
}