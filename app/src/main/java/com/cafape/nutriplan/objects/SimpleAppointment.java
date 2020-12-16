package com.cafape.nutriplan.objects;

import com.cafape.nutriplan.Globals;
import com.cafape.nutriplan.database.entities.AppointmentEntity;
import com.cafape.nutriplan.support.Utils;

import java.io.Serializable;

import static com.cafape.nutriplan.Globals.TIMEFORMAT;

public class SimpleAppointment implements Serializable
{
    private int year;
    private int month;
    private int day;
    private String displayText;
    private int appointmentID;
    private int patientID;

    public SimpleAppointment(AppointmentEntity appointmentEntity, String patient_info) {
        String visitReason = appointmentEntity.getVisitReason();
        if(!visitReason.isEmpty()) {
            visitReason = ": " + visitReason;
        }
        String time = Utils.convertDateFormat(appointmentEntity.getAppointmentTime(), TIMEFORMAT);
        String name_surname = patient_info;
        this.displayText = time + " " + Globals.LONG_DASH + " " + name_surname + visitReason;
        this.appointmentID = appointmentEntity.getAppointmentID();
        this.patientID = appointmentEntity.getPatientID_ref();
        this.year = Integer.parseInt(Utils.convertDateFormat(appointmentEntity.getAppointmentTime(), "yyyy"));
        this.month = Integer.parseInt(Utils.convertDateFormat(appointmentEntity.getAppointmentTime(), "MM"));
        this.day = Integer.parseInt(Utils.convertDateFormat(appointmentEntity.getAppointmentTime(), "dd"));
    }

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
