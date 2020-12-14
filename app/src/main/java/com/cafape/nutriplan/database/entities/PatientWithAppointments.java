package com.cafape.nutriplan.database.entities;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.Date;
import java.util.List;

public class PatientWithAppointments
{
    @Embedded public PatientEntity patientEntity;
    @Relation(
            parentColumn = "patiendID",
            entityColumn = "patientID_ref",
            entity = AppointmentEntity.class
    )
    public List<AppointmentEntity> appointments;
}
