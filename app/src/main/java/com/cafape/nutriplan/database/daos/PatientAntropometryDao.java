package com.cafape.nutriplan.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cafape.nutriplan.database.entities.PatientAnamnesisEntity;
import com.cafape.nutriplan.database.entities.PatientAntropometryEntity;

@Dao
public interface PatientAntropometryDao
{

    @Query("SELECT * FROM PatientAntropometryEntity WHERE patient_ref_ID = :patientID_target ORDER BY antropometryTime DESC LIMIT 1")
    PatientAntropometryEntity getLastPatientAntropometry(long patientID_target);

    @Query("SELECT * FROM PatientAntropometryEntity WHERE patient_ref_ID = :patientID_target ORDER BY antropometryTime DESC")
    PatientAntropometryEntity getAllPatientAntropometry(long patientID_target);

    @Insert
    long insert(PatientAntropometryEntity patientAntropometryEntity);

    @Update
    void update(PatientAntropometryEntity patientAntropometryEntity);

    @Delete
    void delete(PatientAntropometryEntity patientAntropometryEntity);
}
