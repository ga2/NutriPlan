package com.cafape.nutriplan;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;

import com.cafape.nutriplan.ui.CustomDatePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ActivityAppointments extends AppCompatActivity
{
    CustomDatePicker activityappointments_datePicker;
    TextView activityappointments_textView_day;
    TextView activityappointments_textView_monthYear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        setUiComponents();
        setListeners();
    }

    public void setUiComponents() {
        activityappointments_datePicker = findViewById(R.id.activityappointments_datePicker);
        activityappointments_textView_day = findViewById(R.id.activityappointments_textView_day);
        activityappointments_textView_monthYear = findViewById(R.id.activityappointments_textView_monthYear);
    }

    public void setListeners() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        SimpleDateFormat dateFormat_month = new SimpleDateFormat( "LLLL", Locale.getDefault());
        String month_name = dateFormat_month.format(calendar.getTime());
        int year_int = calendar.get(Calendar.YEAR);
        int month_int = calendar.get(Calendar.MONTH);
        int day_int = calendar.get(Calendar.DAY_OF_MONTH);
        setDate(String.valueOf(day_int), month_name, String.valueOf(year_int));
        activityappointments_datePicker.init(year_int, month_int, day_int, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);
                SimpleDateFormat dateFormat = new SimpleDateFormat("LLLL", Locale.getDefault());
                dateFormat.format(calendar.getTime());
                setDate(String.valueOf(dayOfMonth), month_name, String.valueOf(year));
            }
        });
    }

    public void setDate(String day, String month, String year) {
        activityappointments_textView_day.setText(day);
        activityappointments_textView_monthYear.setText(month + " " + year);
    }
}