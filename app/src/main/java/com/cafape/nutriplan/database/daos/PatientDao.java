package com.cafape.nutriplan.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cafape.nutriplan.database.entities.PatientEntity;

import java.util.List;

@Dao
public interface PatientDao
{


    @Query("SELECT * FROM PatientEntity ORDER BY surname ASC")
    List<PatientEntity> getAllPatients();

    @Query("SELECT * FROM PatientEntity WHERE patiendID =:patientID_target LIMIT 1")
    LiveData<PatientEntity> getPatient(int patientID_target);

    @Insert
    void insertPatient(PatientEntity patientEntity);

    @Update
    void updatePatient(PatientEntity patientEntity);

    @Delete
    void deletePatient(PatientEntity patientEntity);
}
