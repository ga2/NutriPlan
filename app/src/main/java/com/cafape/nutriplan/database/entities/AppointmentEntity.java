package com.cafape.nutriplan.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class AppointmentEntity implements Serializable, Comparable<AppointmentEntity>
{
    @PrimaryKey(autoGenerate = true)
    private int appointmentID;
    private Date appointmentTime;
    private String visitReason;
    private int patientID_ref;

    public AppointmentEntity() {}

    public AppointmentEntity(Date appointmentTime, String visitReason, int patientID_ref) {
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

    public int getPatientID_ref() {
        return patientID_ref;
    }

    public void setPatientID_ref(int patientID_ref) {
        this.patientID_ref = patientID_ref;
    }

    @Override
    public int compareTo(AppointmentEntity appointmentEntity) {
        Date date_toCompare = appointmentEntity.getAppointmentTime();
        if(date_toCompare != null) {
            return getAppointmentTime().compareTo(date_toCompare);
        }
        return 0;
    }
}
