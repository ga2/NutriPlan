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
    @Query("SELECT * FROM AppointmentEntity WHERE appointmentTime BETWEEN :from AND :to ORDER BY appointmentTime ASC")
    List<PatientEntity> getAppointmentsForMonth(Date from, Date to);

    @Query("SELECT * FROM AppointmentEntity WHERE appointmentTime =:day")
    LiveData<AppointmentEntity> getAppointmentOfDay(Date day);

    @Insert
    void insertPatient(AppointmentEntity appointmentEntity);

    @Update
    void updatePatient(AppointmentEntity appointmentEntity);

    @Delete
    void deletePatient(AppointmentEntity appointmentEntity);
}
