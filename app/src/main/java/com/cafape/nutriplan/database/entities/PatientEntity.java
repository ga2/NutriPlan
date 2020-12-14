package com.cafape.nutriplan.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.cafape.nutriplan.database.converters.TimestampConverter;
import com.cafape.nutriplan.support.Utils;

import java.io.Serializable;
import java.util.Date;

import static com.cafape.nutriplan.Globals.DATEFORMAT_DISPLAY;

@Entity
public class PatientEntity implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    private int patiendID;
    private String name;
    private String surname;
    @TypeConverters({TimestampConverter.class})
    private Date birthDate;
    private String sex;
    private String phone;
    private String visitReason;
    private boolean previousDiets_status;
    private String previousDiets_details;
    private String weightHistory;
    private boolean previousPathologies_status;
    private String previousPathologies_details;
    private boolean hereditaryPathologies_status;
    private String hereditaryPathologies_details;
    private boolean allergies_status;
    private String allergies_details;
    private boolean productsAssumption_status;
    private String productsAssumption_details;
    private int instestine;
    private int menstrualCycle;
    private boolean physicalActivity_status;
    private String physicalActivity_details;
    private boolean workingActivity_status;
    private String workinglActivity_details;

    public PatientEntity() {}

    public PatientEntity(String name, String surname, Date birthDate, String sex, String phone, String visitReason, boolean previousDiets_status, String previousDiets_details, String weightHistory, boolean previousPathologies_status, String previousPathologies_details, boolean hereditaryPathologies_status, String hereditaryPathologies_details, boolean allergies_status, String allergies_details, boolean productsAssumption_status, String productsAssumption_details, int instestine, int menstrualCycle, boolean physicalActivity_status, String physicalActivity_details, boolean workingActivity_status, String workinglActivity_details) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
        this.sex = sex;
        this.phone = phone;
        this.visitReason = visitReason;
        this.previousDiets_status = previousDiets_status;
        this.previousDiets_details = previousDiets_details;
        this.weightHistory = weightHistory;
        this.previousPathologies_status = previousPathologies_status;
        this.previousPathologies_details = previousPathologies_details;
        this.hereditaryPathologies_status = hereditaryPathologies_status;
        this.hereditaryPathologies_details = hereditaryPathologies_details;
        this.allergies_status = allergies_status;
        this.allergies_details = allergies_details;
        this.productsAssumption_status = productsAssumption_status;
        this.productsAssumption_details = productsAssumption_details;
        this.instestine = instestine;
        this.menstrualCycle = menstrualCycle;
        this.physicalActivity_status = physicalActivity_status;
        this.physicalActivity_details = physicalActivity_details;
        this.workingActivity_status = workingActivity_status;
        this.workinglActivity_details = workinglActivity_details;
    }

    public int getPatiendID() {
        return patiendID;
    }

    public void setPatiendID(int patiendID) {
        this.patiendID = patiendID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVisitReason() {
        return visitReason;
    }

    public void setVisitReason(String visitReason) {
        this.visitReason = visitReason;
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

    public String getWeightHistory() {
        return weightHistory;
    }

    public void setWeightHistory(String weightHistory) {
        this.weightHistory = weightHistory;
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

    public String getHereditaryPathologies_details() {
        return hereditaryPathologies_details;
    }

    public void setHereditaryPathologies_details(String hereditaryPathologies_details) {
        this.hereditaryPathologies_details = hereditaryPathologies_details;
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

    public boolean isProductsAssumption_status() {
        return productsAssumption_status;
    }

    public void setProductsAssumption_status(boolean productsAssumption_status) {
        this.productsAssumption_status = productsAssumption_status;
    }

    public String getProductsAssumption_details() {
        return productsAssumption_details;
    }

    public void setProductsAssumption_details(String productsAssumption_details) {
        this.productsAssumption_details = productsAssumption_details;
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

    public boolean isWorkingActivity_status() {
        return workingActivity_status;
    }

    public void setWorkingActivity_status(boolean workingActivity_status) {
        this.workingActivity_status = workingActivity_status;
    }

    public String getWorkinglActivity_details() {
        return workinglActivity_details;
    }

    public void setWorkinglActivity_details(String workinglActivity_details) {
        this.workinglActivity_details = workinglActivity_details;
    }

    public String getNameSurnammBday() {
        return name + " " + surname + " - " + Utils.convertDateFormat(birthDate, DATEFORMAT_DISPLAY);
    }
}
