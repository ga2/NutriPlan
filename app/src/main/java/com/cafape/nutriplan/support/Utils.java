package com.cafape.nutriplan.support;

import android.util.Log;
import android.widget.DatePicker;
import android.widget.RadioButton;

import com.cafape.nutriplan.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.cafape.nutriplan.Globals.LONG_DASH;

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

    public static String upperCaseAllFirst(String value) {

        char[] array = value.toCharArray();

        // Uppercase first letter.
        array[0] = Character.toUpperCase(array[0]);

        // Uppercase all letters that follow a whitespace character.
        for (int i = 1; i < array.length; i++) {
            if (Character.isWhitespace(array[i - 1])) {
                array[i] = Character.toUpperCase(array[i]);
            }
        }

        return new String(array);
    }

    public static String calculateBAI(String height, String hipscirc) {
        float height_float = Float.parseFloat(height);
        float hipscirc_float = Float.parseFloat(hipscirc);

        float result = (hipscirc_float / (float)Math.pow(height_float / 100, 1.5f)) - 18;

        if(result > 0 && result < 100) {
            DecimalFormat df = new DecimalFormat("###.##");
            return df.format(result);} else {
            return LONG_DASH;
        }
    }

    public static String calculateBMI(String height, String weight) {
        float height_float = Float.parseFloat(height);
        float weight_float = Float.parseFloat(weight);

        float result = (1.3f * weight_float) / (float)Math.pow(height_float / 100, 2.5f);

        if(result > 0 && result < 100) {
            DecimalFormat df = new DecimalFormat("###.##");
            return df.format(result);
        } else {
            return LONG_DASH;
        }
    }
}
