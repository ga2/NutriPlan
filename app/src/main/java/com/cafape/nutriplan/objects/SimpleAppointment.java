package com.cafape.nutriplan.objects;

import com.cafape.nutriplan.Globals;
import com.cafape.nutriplan.database.entities.AppointmentEntity;
import com.cafape.nutriplan.support.Utils;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import static com.cafape.nutriplan.Globals.TIMEFORMAT;

public class SimpleAppointment implements Serializable
{
    private Date date;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minutes;
    private String visitReason = "";
    private String displayText;
    private long appointmentID;
    private long patientID;

    public SimpleAppointment(AppointmentEntity appointmentEntity, String patient_info) {
        String visitReason = appointmentEntity.getVisitReason();
        this.date = appointmentEntity.getAppointmentTime();
        if(!visitReason.isEmpty()) {
            this.visitReason = visitReason;
            visitReason = ": " + visitReason;
        }
        setDate(date);

        this.appointmentID = appointmentEntity.getAppointmentID();
        this.patientID = appointmentEntity.getPatientID_ref();
        String time = Utils.convertDateFormat(date, "HH:mm");
        String name_surname = patient_info;
        this.displayText = time + " " + Globals.LONG_DASH + " " + name_surname + visitReason;
    }

    /*
    public SimpleAppointment(int year, int month, int day, String time, String name_surname, String visitReason, int appointmentID, int patientID) {
        if(!visitReason.isEmpty()) {
            visitReason = ": " + visitReason;
        }
        this.displayText = time + " " + Globals.LONG_DASH + " " + name_surname + visitReason;
        this.appointmentID = appointmentID;
        this.patientID = patientID;
        this.day = day;
        this.month = month;
        this.year = year;
    }

     */
    public String getDisplayText() {
        return displayText;
    }

    /*
    public String getDisplayText(String ) {
        String visitReason_app = "";
        visitReason_app = ": " + visitReason;
        String time = Utils.convertDateFormat(date, "HH:mm");
        String name_surname = patient_info;
        this.displayText = time + " " + Globals.LONG_DASH + " " + name_surname + visitReason_app;
        return displayText;
    }
    */

    public void generateDisplayText(String name_surname) {
        String visitReason_app = "";
        visitReason_app = ": " + visitReason;
        String time = Utils.convertDateFormat(date, "HH:mm");
        this.displayText = time + " " + Globals.LONG_DASH + " " + name_surname + visitReason_app;
    }

    public void setDisplayText(String displayText) {

        this.displayText = displayText;
    }

    public long getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public long getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinutes() {
        return minutes;
    }

    public void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        this.hour = calendar.get(Calendar.HOUR_OF_DAY);
        this.minutes = calendar.get(Calendar.MINUTE);
        this.year = calendar.get(Calendar.YEAR);
        this.month = calendar.get(Calendar.MONTH) + 1;
        this.day = calendar.get(Calendar.DAY_OF_MONTH);
    }
}
