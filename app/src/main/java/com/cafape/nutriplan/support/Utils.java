package com.cafape.nutriplan.support;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import org.apache.commons.lang3.RandomStringUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static com.cafape.nutriplan.support.Globals.LONG_DASH;

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

    public static boolean askForPermission(Activity appCompatActivity, String permission, Integer requestCode)
    {
        if (ContextCompat.checkSelfPermission(appCompatActivity.getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED)
        {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(appCompatActivity, permission))
            {
                //This is called if user has denied the permission before
                //In this case I am just asking the permission again
                ActivityCompat.requestPermissions(appCompatActivity, new String[]{permission}, requestCode);
            }
            else
            {
                ActivityCompat.requestPermissions(appCompatActivity, new String[]{permission}, requestCode);
            }
            return false;
        }
        else
        {
            return true;
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

    public static Date getCurrentTimeStamp() {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
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

    public static float parseStringToFloat(EditText editText) {
        String string = getStringFromEditText(editText);
        float res = 0.0f;
        if(!string.isEmpty()) {
            string = string.replace(",", ".");
            res = Float.parseFloat(string);
        }
        return res;
    }

    public static String getStringFromEditText(EditText editText) {
        return editText.getText().toString();
    }
    
    public static String getFileUriName(Context context, Uri uri) {
        String displayName = "";
        try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                displayName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                cursor.close();
            }
        }
        return displayName;
    }

    public static String generateRandomAlphanumericString() {
        int length = 20;
        boolean useLetters = true;
        boolean useNumbers = true;
        String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);

        return generatedString;
    }

    public static String getFileExtension(String fileName) {
        String extension = "";

        int i = fileName.lastIndexOf('.');
        int p = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));

        if (i > p)
        {
            extension = fileName.substring(i+1);
            return extension;
        }
        return "";
    }

    public static void copyFile(String src_path, String dst_path) throws IOException {
        File src = new File(src_path);
        File dst = new File(dst_path);
        InputStream in = new FileInputStream(src);
        OutputStream out = new FileOutputStream(dst);

        copyFile(in, out);
    }

    public static void copyFile(InputStream in, File dst_file) throws IOException {
        OutputStream out = new FileOutputStream(dst_file);
        copyFile(in, out);
    }

    public static void copyFile(File src_file, File dst_file) throws IOException {
        InputStream in = new FileInputStream(src_file);
        OutputStream out = new FileOutputStream(dst_file);
        copyFile(in, out);
    }

    public static void copyFile(InputStream in, OutputStream out) throws IOException {
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0)
        {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public static InputStream convertFileToInputStream(File file) {
        InputStream is = null;
        try
        {
            is = new FileInputStream(file.getAbsolutePath());
            //is.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return is;
    }

    public static byte[] convertFileToBytes(InputStream inputStream) throws IOException {
        byte[] b = new byte[1024];
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        int c;
        while ((c = inputStream.read(b)) != -1) {
            os.write(b, 0, c);
        }
        return os.toByteArray();
    }

    public static byte[] convertFileToBytes(File file) throws IOException {
        return convertFileToBytes(convertFileToInputStream(file));
    }
}
