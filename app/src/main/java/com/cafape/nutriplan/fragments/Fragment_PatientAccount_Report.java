package com.cafape.nutriplan.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.cafape.nutriplan.ActivityPatientAccount;
import com.cafape.nutriplan.Globals;
import com.cafape.nutriplan.R;
import com.cafape.nutriplan.database.entities.PatientAntropometryEntity;
import com.cafape.nutriplan.support.Utils;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Fragment_PatientAccount_Report#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_PatientAccount_Report extends Fragment
{
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private Context context;
    private int darkGreenColor;

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
        context = getContext();
        darkGreenColor = getResources().getColor(R.color.primaryDarkColor, context.getTheme());
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_patientaccount_report, container, false);
        setUiComponents(layout);
        return layout;
    }

    public void setUiComponents(View layout) {
        BarChart barChart_weight = layout.findViewById(R.id.fragmentpatientaccount_report_chart_weight);
        BarChart barChart_weist = layout.findViewById(R.id.fragmentpatientaccount_report_chart_weist);
        BarChart barChart_hips = layout.findViewById(R.id.fragmentpatientaccount_report_chart_hips);
        BarChart barChart_arm = layout.findViewById(R.id.fragmentpatientaccount_report_chart_arm);
        BarChart barChart_belly = layout.findViewById(R.id.fragmentpatientaccount_report_chart_belly);
        BarChart barChart_leg = layout.findViewById(R.id.fragmentpatientaccount_report_chart_leg);

        List<PatientAntropometryEntity> patientAntropometryEntities = ((ActivityPatientAccount)getActivity()).getPatientAntropometryEntities();

        int numberOfAntropometries = patientAntropometryEntities.size();
        if(numberOfAntropometries > 5) {
            numberOfAntropometries = 5;
        }

        PatientAntropometryEntity activityPatientAccount_antropometry_first = patientAntropometryEntities.get(0);
        PatientAntropometryEntity activityPatientAccount_antropometry_last = patientAntropometryEntities.get(0);

        if(patientAntropometryEntities.size() > 0) {
            activityPatientAccount_antropometry_first = patientAntropometryEntities.get(patientAntropometryEntities.size() - 1);
        }

            final int weight_graph = 1;
            final int weist_graph = 2;
            final int hips_graph = 3;
            final int arm_graph = 4;
            final int belly_graph = 5;
            final int leg_graph = 6;

            setData(barChart_weight, numberOfAntropometries, patientAntropometryEntities, weight_graph);
            setData(barChart_weist, numberOfAntropometries, patientAntropometryEntities, weist_graph);
            setData(barChart_hips, numberOfAntropometries, patientAntropometryEntities, hips_graph);
            setData(barChart_arm, numberOfAntropometries, patientAntropometryEntities, arm_graph);
            setData(barChart_belly, numberOfAntropometries, patientAntropometryEntities, belly_graph);
            setData(barChart_leg, numberOfAntropometries, patientAntropometryEntities, leg_graph);

            TextView fragmentpatientaccount_report_startingweight_label = layout.findViewById(R.id.fragmentpatientaccount_report_startingweight_label);
            fragmentpatientaccount_report_startingweight_label.setText(getString(R.string.activitypatientaccount_string_report_starting_kg, getString(R.string.weight)));
            TextView fragmentpatientaccount_report_startingweight = layout.findViewById(R.id.fragmentpatientaccount_report_startingweight);
            fragmentpatientaccount_report_startingweight.setText(String.valueOf(activityPatientAccount_antropometry_first.getWeight()));
            TextView fragmentpatientaccount_report_currentweight_label = layout.findViewById(R.id.fragmentpatientaccount_report_currentweight_label);
            fragmentpatientaccount_report_currentweight_label.setText(getString(R.string.activitypatientaccount_string_report_current_kg, getString(R.string.weight)));
            TextView fragmentpatientaccount_report_currentweight = layout.findViewById(R.id.fragmentpatientaccount_report_currentweight);
            fragmentpatientaccount_report_currentweight.setText(String.valueOf(activityPatientAccount_antropometry_last.getWeight()));
            TextView fragmentpatientaccount_report_currentBMI = layout.findViewById(R.id.fragmentpatientaccount_report_currentBMI);
            fragmentpatientaccount_report_currentBMI.setText(String.valueOf(activityPatientAccount_antropometry_last.getBmi()));
            TextView fragmentpatientaccount_report_weightobjective = layout.findViewById(R.id.fragmentpatientaccount_report_weightobjective);
            String string = getString(R.string.activitypatientaccount_string_report_idealweight, String.valueOf(activityPatientAccount_antropometry_last.getPi()), String.valueOf(activityPatientAccount_antropometry_last.getPi() - activityPatientAccount_antropometry_last.getWeight()));
            fragmentpatientaccount_report_weightobjective.setText(string);

            TextView fragmentpatientaccount_report_startingweist_label = layout.findViewById(R.id.fragmentpatientaccount_report_startingweist_label);
            fragmentpatientaccount_report_startingweist_label.setText(getString(R.string.activitypatientaccount_string_report_starting_cm, getString(R.string.weist)));
            TextView fragmentpatientaccount_report_startingweist = layout.findViewById(R.id.fragmentpatientaccount_report_startingweist);
            fragmentpatientaccount_report_startingweist.setText(String.valueOf(activityPatientAccount_antropometry_first.getWeist()));
            TextView fragmentpatientaccount_report_currentweist_label = layout.findViewById(R.id.fragmentpatientaccount_report_currentweist_label);
            fragmentpatientaccount_report_currentweist_label.setText(getString(R.string.activitypatientaccount_string_report_current_cm, getString(R.string.weist)));
            TextView fragmentpatientaccount_report_currentweist = layout.findViewById(R.id.fragmentpatientaccount_report_currentweist);
            fragmentpatientaccount_report_currentweist.setText(String.valueOf(activityPatientAccount_antropometry_last.getWeist()));
            float difference_weist = activityPatientAccount_antropometry_first.getWeist() - activityPatientAccount_antropometry_last.getWeist();
            String res_weist = String.valueOf(difference_weist) + " " + Globals.CENTIMETRES;
            ConstraintLayout fragmentpatientaccount_report_contraintlayout_roundresult_weist = layout.findViewById(R.id.fragmentpatientaccount_report_contraintlayout_roundresult_weist);
            if (difference_weist > 0) {
                fragmentpatientaccount_report_contraintlayout_roundresult_weist.setBackground(context.getDrawable(R.drawable.circle_stroke_red));
            }
            else if (difference_weist == 0) {
                fragmentpatientaccount_report_contraintlayout_roundresult_weist.setBackground(context.getDrawable(R.drawable.circle_stroke_yellow));
            }
            TextView fragmentpatientaccount_report_contraintlayout_roundresult_weist_value = layout.findViewById(R.id.fragmentpatientaccount_report_contraintlayout_roundresult_weist_value);
            fragmentpatientaccount_report_contraintlayout_roundresult_weist_value.setText(res_weist);

            TextView fragmentpatientaccount_report_startinghips_label = layout.findViewById(R.id.fragmentpatientaccount_report_startinghips_label);
            fragmentpatientaccount_report_startinghips_label.setText(getString(R.string.activitypatientaccount_string_report_starting_cm, getString(R.string.hips)));
            TextView fragmentpatientaccount_report_startinghips = layout.findViewById(R.id.fragmentpatientaccount_report_startinghips);
            fragmentpatientaccount_report_startinghips.setText(String.valueOf(activityPatientAccount_antropometry_first.getHips()));
            TextView fragmentpatientaccount_report_currenthips_label = layout.findViewById(R.id.fragmentpatientaccount_report_currenthips_label);
            fragmentpatientaccount_report_currenthips_label.setText(getString(R.string.activitypatientaccount_string_report_current_cm, getString(R.string.hips)));
            TextView fragmentpatientaccount_report_currenthips = layout.findViewById(R.id.fragmentpatientaccount_report_currenthips);
            fragmentpatientaccount_report_currenthips.setText(String.valueOf(activityPatientAccount_antropometry_last.getHips()));
            float difference_hips = activityPatientAccount_antropometry_first.getHips() - activityPatientAccount_antropometry_last.getHips();
            String res_hips = String.valueOf(difference_hips) + " " + Globals.CENTIMETRES;
            ConstraintLayout fragmentpatientaccount_report_contraintlayout_roundresult_hips = layout.findViewById(R.id.fragmentpatientaccount_report_contraintlayout_roundresult_hips);
            if (difference_hips > 0) {
                fragmentpatientaccount_report_contraintlayout_roundresult_hips.setBackground(context.getDrawable(R.drawable.circle_stroke_red));
            }
            else if (difference_hips == 0) {
                fragmentpatientaccount_report_contraintlayout_roundresult_hips.setBackground(context.getDrawable(R.drawable.circle_stroke_yellow));
            }
            TextView fragmentpatientaccount_report_contraintlayout_roundresult_hips_value = layout.findViewById(R.id.fragmentpatientaccount_report_contraintlayout_roundresult_hips_value);
            fragmentpatientaccount_report_contraintlayout_roundresult_hips_value.setText(res_hips);


            TextView fragmentpatientaccount_report_startingarm_label = layout.findViewById(R.id.fragmentpatientaccount_report_startingarm_label);
            fragmentpatientaccount_report_startingarm_label.setText(getString(R.string.activitypatientaccount_string_report_starting_cm, getString(R.string.arm)));
            TextView fragmentpatientaccount_report_startingarm = layout.findViewById(R.id.fragmentpatientaccount_report_startingarm);
            fragmentpatientaccount_report_startingarm.setText(String.valueOf(activityPatientAccount_antropometry_first.getArm()));
            TextView fragmentpatientaccount_report_currentarm_label = layout.findViewById(R.id.fragmentpatientaccount_report_currentarm_label);
            fragmentpatientaccount_report_currentarm_label.setText(getString(R.string.activitypatientaccount_string_report_current_cm, getString(R.string.arm)));
            TextView fragmentpatientaccount_report_currentarm = layout.findViewById(R.id.fragmentpatientaccount_report_currentarm);
            fragmentpatientaccount_report_currentarm.setText(String.valueOf(activityPatientAccount_antropometry_last.getArm()));
            float difference_arm = activityPatientAccount_antropometry_first.getArm() - activityPatientAccount_antropometry_last.getArm();
            String res_arm = String.valueOf(difference_arm) + " " + Globals.CENTIMETRES;
            ConstraintLayout fragmentpatientaccount_report_contraintlayout_roundresult_arm = layout.findViewById(R.id.fragmentpatientaccount_report_contraintlayout_roundresult_arm);
            if (difference_arm > 0) {
                fragmentpatientaccount_report_contraintlayout_roundresult_arm.setBackground(context.getDrawable(R.drawable.circle_stroke_red));
            }
            else if (difference_arm == 0) {
                fragmentpatientaccount_report_contraintlayout_roundresult_arm.setBackground(context.getDrawable(R.drawable.circle_stroke_yellow));
            }
            TextView fragmentpatientaccount_report_contraintlayout_roundresult_arm_value = layout.findViewById(R.id.fragmentpatientaccount_report_contraintlayout_roundresult_arm_value);
            fragmentpatientaccount_report_contraintlayout_roundresult_arm_value.setText(res_arm);


            TextView fragmentpatientaccount_report_startingbelly_label = layout.findViewById(R.id.fragmentpatientaccount_report_startingbelly_label);
            fragmentpatientaccount_report_startingbelly_label.setText(getString(R.string.activitypatientaccount_string_report_starting_cm, getString(R.string.belly)));
            TextView fragmentpatientaccount_report_startingbelly = layout.findViewById(R.id.fragmentpatientaccount_report_startingbelly);
            fragmentpatientaccount_report_startingbelly.setText(String.valueOf(activityPatientAccount_antropometry_first.getBelly()));
            TextView fragmentpatientaccount_report_currentbelly_label = layout.findViewById(R.id.fragmentpatientaccount_report_currentbelly_label);
            fragmentpatientaccount_report_currentbelly_label.setText(getString(R.string.activitypatientaccount_string_report_current_cm, getString(R.string.belly)));
            TextView fragmentpatientaccount_report_currentbelly = layout.findViewById(R.id.fragmentpatientaccount_report_currentbelly);
            fragmentpatientaccount_report_currentbelly.setText(String.valueOf(activityPatientAccount_antropometry_last.getBelly()));
            float difference_belly = activityPatientAccount_antropometry_first.getBelly() - activityPatientAccount_antropometry_last.getBelly();
            String res_belly = String.valueOf(difference_belly) + " " + Globals.CENTIMETRES;
            ConstraintLayout fragmentpatientaccount_report_contraintlayout_roundresult_belly = layout.findViewById(R.id.fragmentpatientaccount_report_contraintlayout_roundresult_belly);
            if (difference_belly > 0) {
                fragmentpatientaccount_report_contraintlayout_roundresult_belly.setBackground(context.getDrawable(R.drawable.circle_stroke_red));
            }
            else if (difference_belly == 0) {
                fragmentpatientaccount_report_contraintlayout_roundresult_belly.setBackground(context.getDrawable(R.drawable.circle_stroke_yellow));
            }
            TextView fragmentpatientaccount_report_contraintlayout_roundresult_belly_value = layout.findViewById(R.id.fragmentpatientaccount_report_contraintlayout_roundresult_belly_value);
            fragmentpatientaccount_report_contraintlayout_roundresult_belly_value.setText(res_belly);

            TextView fragmentpatientaccount_report_startingleg_label = layout.findViewById(R.id.fragmentpatientaccount_report_startingleg_label);
            fragmentpatientaccount_report_startingleg_label.setText(getString(R.string.activitypatientaccount_string_report_starting_cm, getString(R.string.leg)));
            TextView fragmentpatientaccount_report_startingleg = layout.findViewById(R.id.fragmentpatientaccount_report_startingleg);
            fragmentpatientaccount_report_startingleg.setText(String.valueOf(activityPatientAccount_antropometry_first.getLeg()));
            TextView fragmentpatientaccount_report_currentleg_label = layout.findViewById(R.id.fragmentpatientaccount_report_currentleg_label);
            fragmentpatientaccount_report_currentleg_label.setText(getString(R.string.activitypatientaccount_string_report_current_cm, getString(R.string.leg)));
            TextView fragmentpatientaccount_report_currentleg = layout.findViewById(R.id.fragmentpatientaccount_report_currentleg);
            fragmentpatientaccount_report_currentleg.setText(String.valueOf(activityPatientAccount_antropometry_last.getLeg()));
            float difference_leg = activityPatientAccount_antropometry_first.getLeg() - activityPatientAccount_antropometry_last.getLeg();
            String res_leg = String.valueOf(difference_leg) + " " + Globals.CENTIMETRES;
            ConstraintLayout fragmentpatientaccount_report_contraintlayout_roundresult_leg = layout.findViewById(R.id.fragmentpatientaccount_report_contraintlayout_roundresult_leg);
            if (difference_leg > 0) {
                fragmentpatientaccount_report_contraintlayout_roundresult_leg.setBackground(context.getDrawable(R.drawable.circle_stroke_red));
            }
            else if (difference_leg == 0) {
                fragmentpatientaccount_report_contraintlayout_roundresult_leg.setBackground(context.getDrawable(R.drawable.circle_stroke_yellow));
            }
            TextView fragmentpatientaccount_report_contraintlayout_roundresult_leg_value = layout.findViewById(R.id.fragmentpatientaccount_report_contraintlayout_roundresult_leg_value);
            fragmentpatientaccount_report_contraintlayout_roundresult_leg_value.setText(res_leg);
    }

    public String getStringFromEditText(EditText editText) {
        return editText.getText().toString();
    }



    private void setData(BarChart chart, int count, List<PatientAntropometryEntity> patientAntropometryEntities, int type) {

        //Graphics setup
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(5);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new MyValueFormatter());

        ArrayList<BarEntry> values = new ArrayList<>();

        int i;
        count = count - 1;

        switch (type) {
            case 1: values = populateArrayListWeight(count, patientAntropometryEntities);
            break;
            case 2: values = populateArrayListWeist(count, patientAntropometryEntities);
            break;
            case 3: values = populateArrayListHips(count, patientAntropometryEntities);
            break;
            case 4: values = populateArrayListArm(count, patientAntropometryEntities);
            break;
            case 5: values = populateArrayListBelly(count, patientAntropometryEntities);
            break;
            case 6: values = populateArrayListLeg(count, patientAntropometryEntities);
        }

        BarDataSet barDataSet;
        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            barDataSet = (BarDataSet) chart.getData().getDataSetByIndex(0);
            barDataSet.setValues(values);
            barDataSet.setColor(darkGreenColor);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            barDataSet = new BarDataSet(values, "Performances");
            barDataSet.setColor(darkGreenColor);

            barDataSet.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(barDataSet);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            //float barWidth = 60f;
            float barWidth = 10f;
            data.setBarWidth(barWidth);

            chart.setData(data);
            xAxis.setAxisMinimum(barDataSet.getXMin()-barWidth/2);
            xAxis.setAxisMaximum(barDataSet.getXMax()+barWidth/2);
        }
    }

    private ArrayList<BarEntry> populateArrayListWeight (int count, List<PatientAntropometryEntity> activityPatientAccountList) {
        ArrayList<BarEntry> values = new ArrayList<>();
        int i;
        for (i = count; i >= 0; i--) {
            float data_x = (activityPatientAccountList.get(i).getAntropometryTime().getTime());
            float data_y = activityPatientAccountList.get(i).getWeight();
            values.add(new BarEntry(data_x / 1000000, data_y));
        }
        return values;
    }

    private ArrayList<BarEntry> populateArrayListWeist (int count, List<PatientAntropometryEntity> activityPatientAccountList) {
        ArrayList<BarEntry> values = new ArrayList<>();
        int i;
        for (i = count; i >= 0; i--) {
            float data_x = (activityPatientAccountList.get(i).getAntropometryTime().getTime());
            float data_y = activityPatientAccountList.get(i).getWeist();
            values.add(new BarEntry(data_x / 1000000, data_y));
        }
        return values;
    }

    private ArrayList<BarEntry> populateArrayListHips (int count, List<PatientAntropometryEntity> activityPatientAccountList) {
        ArrayList<BarEntry> values = new ArrayList<>();
        int i;
        for (i = count; i >= 0; i--) {
            float data_x = (activityPatientAccountList.get(i).getAntropometryTime().getTime());
            float data_y = activityPatientAccountList.get(i).getHips();
            values.add(new BarEntry(data_x / 1000000, data_y));
        }
        return values;
    }

    private ArrayList<BarEntry> populateArrayListArm (int count, List<PatientAntropometryEntity> activityPatientAccountList) {
        ArrayList<BarEntry> values = new ArrayList<>();
        int i;
        for (i = count; i >= 0; i--) {
            float data_x = (activityPatientAccountList.get(i).getAntropometryTime().getTime());
            float data_y = activityPatientAccountList.get(i).getArm();
            values.add(new BarEntry(data_x / 1000000, data_y));
        }
        return values;
    }

    private ArrayList<BarEntry> populateArrayListBelly (int count, List<PatientAntropometryEntity> activityPatientAccountList) {
        ArrayList<BarEntry> values = new ArrayList<>();
        int i;
        for (i = count; i >= 0; i--) {
            float data_x = (activityPatientAccountList.get(i).getAntropometryTime().getTime());
            float data_y = activityPatientAccountList.get(i).getBelly();
            values.add(new BarEntry(data_x / 1000000, data_y));
        }
        return values;
    }

    private ArrayList<BarEntry> populateArrayListLeg (int count, List<PatientAntropometryEntity> activityPatientAccountList) {
        ArrayList<BarEntry> values = new ArrayList<>();
        int i;
        for (i = count; i >= 0; i--) {
            float data_x = (activityPatientAccountList.get(i).getAntropometryTime().getTime());
            float data_y = activityPatientAccountList.get(i).getLeg();
            values.add(new BarEntry(data_x / 1000000, data_y));
        }
        return values;
    }
/*
    private void setData(BarChart chart, int count, float range) {

        ///getWeight history
        //
        count = 5;

        //Graphics setup
        chart.setDrawBarShadow(false);
        chart.setDrawValueAboveBar(true);
        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawGridLines(false);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);

        //chart.getAxisRight().setXOffset(20f);

        //x-axis setup


        float[] dates = new float[7];
        dates[0] = 1577833200000f;
        dates[1] = 1580511600000f;
        dates[2] = 1583017200000f;
        dates[3] = 1585692000000f;
        dates[4] = 1588284000000f;
        dates[5] = 1590962400000f;
        dates[6] = 1593554400000f;

        long[] datesL = new long[7];
        dates[0] = 1577833200000L;
        dates[1] = 1580511600000L;
        dates[2] = 1583017200000L;
        dates[3] = 1585692000000L;
        dates[4] = 1588284000000L;
        dates[5] = 1590962400000L;
        dates[6] = 1593554400000L;

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setValueFormatter(new MyValueFormatter());

        float start = 1f;

        ArrayList<BarEntry> values = new ArrayList<>();

        for (int i = (int) start; i < start + count; i++) {
            float val = (float) (Math.random() * (range + 1));

            values.add(new BarEntry(dates[i] / 1000000, val));
            //values.add(new BarEntry(i, val));
        }

        BarDataSet set1;

       // chart.getXAxis().setLabelCount(5);


        if (chart.getData() != null && chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            set1.setColor(darkGreenColor);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Performances");
            set1.setColor(darkGreenColor);

            set1.setDrawIcons(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            //data.setValueTypeface(tfLight);
            //data.setBarWidth(0.9f);
            data.setBarWidth(800f);

            chart.setData(data);
        }
    }
 */
    public class MyValueFormatter extends ValueFormatter {
        @Override
        public String getFormattedValue(float value_long) {
            Date date = new Date((long)value_long * 1000000);
            return Utils.convertDateFormat(date, Globals.DATEFORMAT_DISPLAY);
        }
    }


}