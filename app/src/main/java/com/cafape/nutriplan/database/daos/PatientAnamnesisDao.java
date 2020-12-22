package com.cafape.nutriplan.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cafape.nutriplan.database.entities.PatientAnamnesisEntity;
import com.cafape.nutriplan.database.entities.PatientEntity;

@Dao
public interface PatientAnamnesisDao
{

    @Query("SELECT * FROM PatientAnamnesisEntity WHERE patient_ref_ID = :patientID_target LIMIT 1")
    PatientAnamnesisEntity getPatientAnamnesis(long patientID_target);

    @Query("UPDATE PatientAnamnesisEntity SET welcomefood = :welcomeFood, nonwelcomefood = :nonWelcomeFood WHERE anamnesisID = :anamnesisID")
    void updateWithID(long anamnesisID, String welcomeFood, String nonWelcomeFood);

    @Insert
    long insert(PatientAnamnesisEntity patientAnamnesisEntity);

    @Update
    void update(PatientAnamnesisEntity patientAnamnesisEntity);

    @Delete
    void delete(PatientAnamnesisEntity patientAnamnesisEntity);
}
