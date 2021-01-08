package com.cafape.nutriplan;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

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

    public void showBMITable() {
        AlertDialog.Builder alert_bmi = new AlertDialog.Builder(ActivityCalc.this);
        LayoutInflater factory = LayoutInflater.from(ActivityCalc.this);
        final View view_bmi = factory.inflate(R.layout.table_bmi, null);
        alert_bmi.setView(view_bmi);
        alert_bmi.show();
    }

    public void showBAITable() {
        AlertDialog.Builder alert_bai = new AlertDialog.Builder(ActivityCalc.this);
        LayoutInflater factory = LayoutInflater.from(ActivityCalc.this);
        final View view_bai = factory.inflate(R.layout.table_bai, null);
        alert_bai.setView(view_bai);
        alert_bai.show();
    }
}