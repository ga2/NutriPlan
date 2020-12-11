package com.cafape.nutriplan.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cafape.nutriplan.database.entities.AppointmentEntity;
import com.cafape.nutriplan.database.entities.PatientEntity;

import java.util.Date;
import java.util.List;

@Dao
public interface AppointmentDao
{
    @Query("SELECT * FROM AppointmentEntity WHERE strftime('%m', appointmentTime) = :monthNumber ORDER BY appointmentTime ASC")
    List<AppointmentEntity> getAppointmentsForMonth(String monthNumber);

    @Query("SELECT * FROM AppointmentEntity WHERE appointmentTime =:day")
    LiveData<AppointmentEntity> getAppointmentOfDay(Date day);

    @Insert
    void insertPatient(AppointmentEntity appointmentEntity);

    @Update
    void updatePatient(AppointmentEntity appointmentEntity);

    @Delete
    void deletePatient(AppointmentEntity appointmentEntity);
}
