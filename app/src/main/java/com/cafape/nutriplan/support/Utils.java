package com.cafape.nutriplan.support;

import android.util.Log;
import android.widget.DatePicker;

import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utils
{
    public static void myprint(Object object)
    {
        if(object == null) {
            Log.d("myprint", "null");
        } else {
            Log.d("myprint", object.toString());
        }
    }

    public static java.util.Date getDateFromDatePicker(DatePicker datePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    public static String convertDateFormat(Date date, String format) {
        try {
            Date currentLocalTime = date;
            DateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
            String localTime = dateFormat.format(currentLocalTime);
            return localTime;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getMonthName(Calendar calendar) {
        SimpleDateFormat dateFormat_month = new SimpleDateFormat( "LLLL", Locale.getDefault());
        return dateFormat_month.format(calendar.getTime());
    }

    public static int calculateAge(Date bdate) {
        Calendar today = Calendar.getInstance();
        Calendar birthDate_calendar = Calendar.getInstance();

        int age = 0;

        birthDate_calendar.setTime(bdate);
        if (birthDate_calendar.after(today)) {
            //throw new IllegalArgumentException("Can't be born in the future");
            return -1;
        }

        age = today.get(Calendar.YEAR) - birthDate_calendar.get(Calendar.YEAR);

        // If birth date is greater than todays date (after 2 days adjustment of leap year) then decrement age one year
        if ( (birthDate_calendar.get(Calendar.DAY_OF_YEAR) - today.get(Calendar.DAY_OF_YEAR) > 3) ||
                (birthDate_calendar.get(Calendar.MONTH) > today.get(Calendar.MONTH ))){
            age--;

            // If birth date and todays date are of same month and birth day of month is greater than todays day of month then decrement age
        }else if ((birthDate_calendar.get(Calendar.MONTH) == today.get(Calendar.MONTH )) &&
                (birthDate_calendar.get(Calendar.DAY_OF_MONTH) > today.get(Calendar.DAY_OF_MONTH ))){
            age--;
        }

        return age;
    }
}
