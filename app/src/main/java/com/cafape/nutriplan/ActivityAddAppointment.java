package com.cafape.nutriplan;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.TimePicker;

public class ActivityAddAppointment extends AppCompatActivity
{
    private TimePicker activityaddappointment_timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_appointment);

        setUiComponents();
        setListeners();
    }

    public void setUiComponents() {
        activityaddappointment_timePicker = findViewById(R.id.activityaddappointment_timePicker);
    }

    public void setListeners() {
        activityaddappointment_timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener()
        {
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {

            }
        });
    }
}