package com.cafape.nutriplan.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class AppointmentEntity implements Serializable, Comparable<AppointmentEntity>
{
    @PrimaryKey(autoGenerate = true)
    private long appointmentID;
    private Date appointmentTime;
    private String visitReason;
    private long patientID_ref;

    public AppointmentEntity() {}

    public AppointmentEntity(Date appointmentTime, String visitReason, long patientID_ref) {
        this.appointmentTime = appointmentTime;
        this.visitReason = visitReason;
        this.patientID_ref = patientID_ref;
    }

    public long getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(long appointmentID) {
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

    public long getPatientID_ref() {
        return patientID_ref;
    }

    public void setPatientID_ref(long patientID_ref) {
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
