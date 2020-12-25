package com.cafape.nutriplan.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.cafape.nutriplan.database.converters.TimestampConverter;
import com.cafape.nutriplan.database.daos.AppointmentDao;
import com.cafape.nutriplan.database.daos.FileDao;
import com.cafape.nutriplan.database.daos.PatientAnamnesisDao;
import com.cafape.nutriplan.database.daos.PatientAntropometryDao;
import com.cafape.nutriplan.database.daos.PatientDao;
import com.cafape.nutriplan.database.daos.PatientWithAppointmentsDao;
import com.cafape.nutriplan.database.entities.AppointmentEntity;
import com.cafape.nutriplan.database.entities.FileEntity;
import com.cafape.nutriplan.database.entities.PatientAnamnesisEntity;
import com.cafape.nutriplan.database.entities.PatientAntropometryEntity;
import com.cafape.nutriplan.database.entities.PatientEntity;

@Database(entities = {PatientEntity.class, FileEntity.class, AppointmentEntity.class, PatientAnamnesisEntity.class, PatientAntropometryEntity.class}, version = 2, exportSchema = false)
@TypeConverters({TimestampConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract PatientDao patientDao();
    public abstract AppointmentDao appointmentDao();
    public abstract PatientWithAppointmentsDao patientWithAppointmentsDao();
    public abstract PatientAnamnesisDao patientAnamnesisDao();
    public abstract PatientAntropometryDao patientAntropometryDao();
    public abstract FileDao fileDao();
}
