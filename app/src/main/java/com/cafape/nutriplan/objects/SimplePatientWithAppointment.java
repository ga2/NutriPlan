package com.cafape.nutriplan.objects;

import com.cafape.nutriplan.database.entities.AppointmentEntity;
import com.cafape.nutriplan.database.entities.PatientEntity;

import java.util.Date;

public class SimplePatientWithAppointment
{
    private PatientEntity patientEntity;
    private Date visit = null;

    public SimplePatientWithAppointment(PatientEntity patientEntity, AppointmentEntity appointmentEntity) {
        this.patientEntity = patientEntity;
        if(appointmentEntity != null)
            this.visit = appointmentEntity.getAppointmentTime();
    }

    public PatientEntity getPatientEntity() {
        return patientEntity;
    }

    public void setPatientEntity(PatientEntity patientEntity) {
        this.patientEntity = patientEntity;
    }

    public Date getVisit() {
        return visit;
    }

    public void setVisit(Date visit) {
        this.visit = visit;
    }
}
