package com.cafape.nutriplan.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class PatientEntity implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    private int patiendID;
    private String surname;
    private String name;
    private String telefono;
    private Date birthDate;
    private char sex;
    private String visitReason;
    private String weightHistory;
    private boolean previousDiets_status;
    private String previousDiets_details;
    private boolean previousPathologies_status;
    private String previousPathologies_details;
    private boolean hereditaryPathologies_status;
    private String herditaryPathologies_details;
    private boolean allergies_status;
    private String allergies_details;
    private int instestine;
    private int menstrualCycle;
    private boolean physicalActivity_status;
    private String physicalActivity_details;

    public PatientEntity(){

    }

    public PatientEntity(int patiendID, String surname, String name, String telefono, Date birthDate, char sex, String visitReason, String weightHistory, boolean previousDiets_status, String previousDiets_details, boolean previousPathologies_status, String previousPathologies_details, boolean hereditaryPathologies_status, String herditaryPathologies_details, boolean allergies_status, String allergies_details, int instestine, int menstrualCycle, boolean physicalActivity_status, String physicalActivity_details) {
        this.patiendID = patiendID;
        this.surname = surname;
        this.name = name;
        this.telefono = telefono;
        this.birthDate = birthDate;
        this.sex = sex;
        this.visitReason = visitReason;
        this.weightHistory = weightHistory;
        this.previousDiets_status = previousDiets_status;
        this.previousDiets_details = previousDiets_details;
        this.previousPathologies_status = previousPathologies_status;
        this.previousPathologies_details = previousPathologies_details;
        this.hereditaryPathologies_status = hereditaryPathologies_status;
        this.herditaryPathologies_details = herditaryPathologies_details;
        this.allergies_status = allergies_status;
        this.allergies_details = allergies_details;
        this.instestine = instestine;
        this.menstrualCycle = menstrualCycle;
        this.physicalActivity_status = physicalActivity_status;
        this.physicalActivity_details = physicalActivity_details;
    }

    public int getPatiendID() {
        return patiendID;
    }

    public void setPatiendID(int patiendID) {
        this.patiendID = patiendID;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
    }

    public String getWeightHistory() {
        return weightHistory;
    }

    public void setWeightHistory(String weightHistory) {
        this.weightHistory = weightHistory;
    }

    public boolean isPreviousDiets_status() {
        return previousDiets_status;
    }

    public void setPreviousDiets_status(boolean previousDiets_status) {
        this.previousDiets_status = previousDiets_status;
    }

    public String getPreviousDiets_details() {
        return previousDiets_details;
    }

    public void setPreviousDiets_details(String previousDiets_details) {
        this.previousDiets_details = previousDiets_details;
    }

    public boolean isPreviousPathologies_status() {
        return previousPathologies_status;
    }

    public void setPreviousPathologies_status(boolean previousPathologies_status) {
        this.previousPathologies_status = previousPathologies_status;
    }

    public String getPreviousPathologies_details() {
        return previousPathologies_details;
    }

    public void setPreviousPathologies_details(String previousPathologies_details) {
        this.previousPathologies_details = previousPathologies_details;
    }

    public boolean isHereditaryPathologies_status() {
        return hereditaryPathologies_status;
    }

    public void setHereditaryPathologies_status(boolean hereditaryPathologies_status) {
        this.hereditaryPathologies_status = hereditaryPathologies_status;
    }

    public String getHerditaryPathologies_details() {
        return herditaryPathologies_details;
    }

    public void setHerditaryPathologies_details(String herditaryPathologies_details) {
        this.herditaryPathologies_details = herditaryPathologies_details;
    }

    public boolean isAllergies_status() {
        return allergies_status;
    }

    public void setAllergies_status(boolean allergies_status) {
        this.allergies_status = allergies_status;
    }

    public String getAllergies_details() {
        return allergies_details;
    }

    public void setAllergies_details(String allergies_details) {
        this.allergies_details = allergies_details;
    }

    public int getInstestine() {
        return instestine;
    }

    public void setInstestine(int instestine) {
        this.instestine = instestine;
    }

    public int getMenstrualCycle() {
        return menstrualCycle;
    }

    public void setMenstrualCycle(int menstrualCycle) {
        this.menstrualCycle = menstrualCycle;
    }

    public boolean isPhysicalActivity_status() {
        return physicalActivity_status;
    }

    public void setPhysicalActivity_status(boolean physicalActivity_status) {
        this.physicalActivity_status = physicalActivity_status;
    }

    public String getPhysicalActivity_details() {
        return physicalActivity_details;
    }

    public void setPhysicalActivity_details(String physicalActivity_details) {
        this.physicalActivity_details = physicalActivity_details;
    }
}
