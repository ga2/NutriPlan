package com.cafape.nutriplan.database.daos;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface ParentWithAppointmentsDao
{
    @Transaction
    @Query("SELECT * FROM PatientEntity")
    public List<ParentWithAppointmentsDao> getUsersWithPlaylists();

}
