package com.cafape.nutriplan.database.daos;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cafape.nutriplan.database.entities.PatientAnamnesisEntity;
import com.cafape.nutriplan.database.entities.PatientEntity;

import java.util.List;

@Dao
public interface PatientDao
{
    @Query("SELECT * FROM PatientEntity ORDER BY surname ASC")
    List<PatientEntity> getAllPatients();

    @Query("SELECT * FROM PatientEntity WHERE patiendID = :patientID_target LIMIT 1")
    PatientEntity getPatient(long patientID_target);

    @Query("SELECT COUNT(*) FROM PatientEntity")
    int getPatientsNumber();

    @Query("UPDATE PatientEntity SET visitReason = :visitReason, previousPathologies_details = :previousPathologiesDetails,  previousDiets_status = :previousDiets_status, hereditaryPathologies_details = :hereditaryPathologiesDetails , hereditaryPathologies_status = :isHereditaryPathologies, allergies_details = :allergiesDetails , allergies_status = :isAllergies , productsAssumption_details = :productAssumption , productsAssumption_status = :isProductAssumption , instestine = :intestine , menstrualCycle = :menstrualCycle, physicalActivity_details = :physicalActivityDetails, physicalActivity_status = :isPhysicalActivity, workinglActivity_details = :workingActivityDetails, workingActivity_status = :isWorkingActivityDetails WHERE patiendID = :patientID")
    void updateWithID(long patientID, String visitReason, String previousPathologiesDetails,
    boolean previousDiets_status, String hereditaryPathologiesDetails,
                      boolean isHereditaryPathologies, String allergiesDetails,
                      boolean isAllergies, String productAssumption,
                      boolean isProductAssumption, int intestine, int menstrualCycle,
                      String physicalActivityDetails, boolean isPhysicalActivity,
                      String workingActivityDetails, boolean isWorkingActivityDetails);

    @Insert
    long insert(PatientEntity patientEntity);

    @Update
    void update(PatientEntity patientEntity);

    @Delete
    void delete(PatientEntity patientEntity);
}
