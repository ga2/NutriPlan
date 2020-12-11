package com.cafape.nutriplan.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.cafape.nutriplan.database.converters.TimestampConverter;
import com.cafape.nutriplan.database.daos.AppointmentDao;
import com.cafape.nutriplan.database.daos.PatientDao;
import com.cafape.nutriplan.database.entities.AppointmentEntity;
import com.cafape.nutriplan.database.entities.PatientEntity;

@Database(entities = {PatientEntity.class, AppointmentEntity.class}, version = 1, exportSchema = false)
@TypeConverters({TimestampConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract PatientDao patientDao();
    public abstract AppointmentDao appointmentDao();
}
