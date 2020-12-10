package com.cafape.nutriplan.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.cafape.nutriplan.database.converters.TimestampConverter;

import java.io.Serializable;
import java.util.Date;

@Entity
public class AppointmentEntity implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    private int appointmentID;
    private String name;
    private Date appointmentTime;
    private String visitReason;
    private int patientID_ref;

    public AppointmentEntity() {}

    public AppointmentEntity(int appointmentID, String name, Date appointmentTime, String visitReason, int patientID_ref) {
        this.appointmentID = appointmentID;
        this.name = name;
        this.appointmentTime = appointmentTime;
        this.visitReason = visitReason;
        this.patientID_ref = patientID_ref;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public int getpatientID_ref() {
        return patientID_ref;
    }

    public void setpatientID_ref(int patientID_ref) {
        this.patientID_ref = patientID_ref;
    }
}
