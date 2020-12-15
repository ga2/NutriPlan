package com.cafape.nutriplan.objects;

import com.cafape.nutriplan.Globals;

public class SimpleAppointment
{
    private int year;
    private int month;
    private int day;
    private String displayText;
    private int appointmentID;
    private int patientID;

    public SimpleAppointment(int year, int month, int day,String time, String name_surname, String visitReason, int appointmentID, int patientID) {
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

    public String getDisplayText() {
        return displayText;
    }

    public void setDisplayText(String displayText) {
        this.displayText = displayText;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public int getPatientID() {
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
}
