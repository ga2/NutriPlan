package com.cafape.nutriplan.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.cafape.nutriplan.database.entities.FileEntity;

import java.util.List;

@Dao
public interface FileDao
{
    @Query("SELECT * FROM FileEntity WHERE patientID_ref = :patientID ORDER BY fileCreationTime ASC")
    List<FileEntity> getFilesByPatient(long patientID);

    @Insert
    void insert(FileEntity appointmentEntity);

    @Update
    void update(FileEntity appointmentEntity);

    @Delete
    void delete(FileEntity appointmentEntity);
}
