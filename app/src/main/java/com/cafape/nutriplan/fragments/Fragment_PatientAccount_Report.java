package com.cafape.nutriplan.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

import com.cafape.nutriplan.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_PatientAccount_Report#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_PatientAccount_Report extends Fragment
{
    private BarChart chart;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_PatientAccount_Report() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_AddPatient_Antropometry.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_PatientAccount_Report newInstance(String param1, String param2) {
        Fragment_PatientAccount_Report fragment = new Fragment_PatientAccount_Report();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_patientaccount_report, container, false);
        setUiComponents(layout);
        setListeners(layout);
        return layout;
    }

    public void setUiComponents(View layout) {
        chart = layout.findViewById(R.id.fragmentpatientaccount_report_chart);
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        setData(20, 14);
    }

    public void setListeners(View layout) {
       /* activityaddpatient_button_backto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewPager viewPager = getActivity().findViewById(R.id.view_pager);
                viewPager.setCurrentItem(1);
            }
        });

        */
    }

    public String getStringFromEditText(EditText editText) {
        return editText.getText().toString();
    }

    private void setData(int count, float range) {
        float start = 1f;

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = (int) start; i < start + count; i++) {
            float val = (float) (Math.random() * (range + 1));

            if (Math.random() * 100 < 25) {
                values.add(new BarEntry(i, val, getResources().getDrawable(R.drawable.circle_background)));
            } else {
                values.add(new BarEntry(i, val));
            }
        }

        BarDataSet set1;

        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.setColor(Color.GREEN);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "The year 2017");
            set1.setColor(Color.GREEN);

            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            //data.setValueTypeface(tfLight);
            data.setBarWidth(0.9f);


            chart.setData(data);
        }
    }
}