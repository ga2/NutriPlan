package com.cafape.nutriplan.database.daos;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.cafape.nutriplan.database.entities.PatientWithAppointments;

import java.util.List;

@Dao
public interface PatientWithAppointmentsDao
{
  /*  @Transaction
    @Query("SELECT * FROM AppointmentEntity AS ae INNER JOIN PatientEntity AS pe ON ae.appointmentID = pe.patiendID WHERE pe.patiendID =:patientID")
    public List<PatientWithAppointmentsDao> getPatientWithAppointments(int patientID);

    @Transaction
    @Query("SELECT * FROM AppointmentEntity AS ae INNER JOIN PatientEntity AS pe ON ae.appointmentID = pe.patiendID WHERE pe.patiendID =:patientID ORDER BY ae.appointmentTime DESC LIMIT 1")
    public List<PatientWithAppointmentsDao> getPatientWithLastAppointments(int patientID);
*/
    @Transaction
    @Query("SELECT pe.patiendID, pe.birthDate, pe.phone, pe.name, pe.surname, ae.appointmentTime FROM PatientEntity AS pe LEFT JOIN AppointmentEntity AS ae ON ae.appointmentID = pe.patiendID ORDER BY pe.surname, ae.appointmentTime DESC")
    public List<PatientWithAppointments> getAllPatientWithLastAppointments();

}
