package com.cafape.nutriplan.database.daos;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.cafape.nutriplan.database.entities.PatientWithAppointments;

import java.util.Date;
import java.util.List;

@Dao
public interface PatientWithAppointmentsDao
{
    @Transaction
    @Query("SELECT pe.patiendID, pe.birthDate, pe.name, pe.surname, ae.visitReason, ae.appointmentTime, ae.appointmentID FROM PatientEntity AS pe INNER JOIN AppointmentEntity AS ae ON ae.patientID_ref = pe.patiendID WHERE (strftime('%Y',ae.appointmentTime) IN (:req_year)) AND (strftime('%m', ae.appointmentTime) IN (:req_month)) ORDER BY ae.appointmentTime ASC")
    public List<PatientWithAppointments> getAllAppointmentsOfTheMonth(String req_year, String req_month);

  @Transaction
  @Query("SELECT pe.patiendID, pe.birthDate, pe.name, pe.surname, ae.visitReason, ae.appointmentTime, ae.appointmentID FROM PatientEntity AS pe INNER JOIN AppointmentEntity AS ae ON ae.patientID_ref = pe.patiendID WHERE (strftime('%Y', ae.appointmentTime) IN (:req_year)) AND (strftime('%m', ae.appointmentTime) IN (:req_month)) AND (strftime('%d', ae.appointmentTime) IN (:req_day)) ORDER BY ae.appointmentTime ASC")
  public List<PatientWithAppointments> getAllAppointmentsOfTheDay(String req_year, String req_month, String req_day);

  @Transaction
  @Query("SELECT pe.patiendID, pe.birthDate, pe.name, pe.surname, ae.visitReason, ae.appointmentTime, ae.appointmentID FROM PatientEntity AS pe JOIN AppointmentEntity AS ae ON ae.patientID_ref = pe.patiendID WHERE strftime('%Y-%m-%d', ae.appointmentTime) = :req_date ORDER BY ae.appointmentTime ASC")
  //@Query("SELECT pe.patiendID, pe.birthDate, pe.name, pe.surname, ae.visitReason, ae.appointmentTime, ae.appointmentID FROM PatientEntity AS pe JOIN AppointmentEntity AS ae ON ae.patientID_ref = pe.patiendID WHERE strftime('%d', ae.appointmentTime) = :req_date ORDER BY ae.appointmentTime ASC")
  //@Query("SELECT pe.patiendID, pe.birthDate, pe.name, pe.surname, ae.visitReason, ae.appointmentTime, ae.appointmentID FROM PatientEntity AS pe JOIN AppointmentEntity AS ae ON ae.patientID_ref = pe.patiendID WHERE ae.appointmentTime = :req_date ORDER BY ae.appointmentTime ASC")
  //public List<PatientWithAppointments> getAllAppointmentsOfTheDay(String req_date);
  public List<PatientWithAppointments> getAllAppointmentsOfTheDay(String req_date);

    @Transaction
    @Query("SELECT pe.patiendID, pe.birthDate, pe.phone, pe.name, pe.surname, ae.appointmentTime FROM PatientEntity AS pe LEFT JOIN AppointmentEntity AS ae ON ae.patientID_ref = pe.patiendID ORDER BY pe.surname, ae.appointmentTime DESC")
    public List<PatientWithAppointments> getAllPatientWithLastAppointments();

}
