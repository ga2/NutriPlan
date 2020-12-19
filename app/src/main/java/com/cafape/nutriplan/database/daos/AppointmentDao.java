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
    @Query("SELECT * FROM AppointmentEntity WHERE strftime('%m', appointmentTime) IN (:req_month) AND strftime('%Y', appointmentTime) IN (:req_year) ORDER BY appointmentTime ASC")
    List<AppointmentEntity> getAppointmentsForMonth(String req_year, String req_month);

    @Query("SELECT * FROM AppointmentEntity WHERE appointmentTime =:day")
    LiveData<AppointmentEntity> getAppointmentOfDay(Date day);

    @Query("SELECT * FROM AppointmentEntity")
    List<AppointmentEntity> getAppointments();

    @Query("SELECT * FROM AppointmentEntity WHERE appointmentID =:appointmentID")
    AppointmentEntity getAppointmentByID(long appointmentID);

    @Query("SELECT * FROM AppointmentEntity WHERE patientID_ref =:patientID ORDER BY appointmentTime DESC LIMIT 1")
    AppointmentEntity getLastAppointmentByPatientID(long patientID);

    @Query("DELETE FROM Appointmententity WHERE appointmentID =:appointmentID")
    int deleteByID(long appointmentID);

    @Query("UPDATE AppointmentEntity SET visitReason = :visit_reason, appointmentTime = :appointment_time WHERE appointmentID =:appointmentID")
    void updateAppointmentByID(long appointmentID, String visit_reason, Date appointment_time);

    @Insert
    void insert(AppointmentEntity appointmentEntity);

    @Update
    void update(AppointmentEntity appointmentEntity);

    @Delete
    void delete(AppointmentEntity appointmentEntity);
}
