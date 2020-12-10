package com.cafape.nutriplan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.cafape.nutriplan.support.Utils;
import com.cafape.nutriplan.ui.CustomDatePicker;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.threeten.bp.LocalDate;
import org.threeten.bp.format.TextStyle;

public class ActivityAppointments extends AppCompatActivity
{
    MaterialCalendarView activityappointments_calendarView;
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
        activityappointments_calendarView = findViewById(R.id.activityappointments_calendarView);
        activityappointments_textView_day = findViewById(R.id.activityappointments_textView_day);
        activityappointments_textView_monthYear = findViewById(R.id.activityappointments_textView_monthYear);
    }

    public void setListeners() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());

        int year_int = calendar.get(Calendar.YEAR);
        int month_int = calendar.get(Calendar.MONTH) + 1;
        int day_int = calendar.get(Calendar.DAY_OF_MONTH);

        LocalDate localDate = LocalDate.of(year_int, month_int, day_int);
        setDate(String.valueOf(day_int), Utils.getMonthName(calendar), String.valueOf(year_int));

        activityappointments_calendarView.setSelectedDate(localDate);
        activityappointments_calendarView.setCurrentDate(localDate);

        activityappointments_calendarView.setOnDateChangedListener(new OnDateSelectedListener()
        {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                setDate(String.valueOf(date.getDay()), date.getDate().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()), String.valueOf(date.getYear()));
            }
        });

        activityappointments_calendarView.setOnMonthChangedListener(new OnMonthChangedListener()
        {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                Toast.makeText(getApplicationContext(), date.getDate().getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault()), Toast.LENGTH_SHORT).show();
            }
        });

        DayViewDecorator dayViewDecorator = new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return day.equals(CalendarDay.from(2021, 1, 25));
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.setBackgroundDrawable(getDrawable(R.drawable.ic_baseline_brightness_8));
                view.addSpan(new ForegroundColorSpan(Color.WHITE));
            }
        };

        dayViewDecorator.shouldDecorate(CalendarDay.from(2021, 1, 25)); //1=gennaio

        activityappointments_calendarView.addDecorator(dayViewDecorator);

        //todo add no data message
    }

    public void setDate(String day, String month, String year) {
        activityappointments_textView_day.setText(day);
        activityappointments_textView_monthYear.setText(month + " " + year);
    }
}