package com.cafape.nutriplan.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.cafape.nutriplan.Globals;
import com.cafape.nutriplan.database.converters.TimestampConverter;
import com.cafape.nutriplan.support.Utils;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

@Entity
public class PatientAntropometryEntity implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    private long antropometryID;
    private long patient_ref_ID;
    @TypeConverters({TimestampConverter.class})
    private Date antropometryTime;
    private float weight;
    private float height;
    private float weist;
    private float belly;
    private float hips;
    private float leg;
    private float arm;
    private float bicipital;
    private float pectoral;
    private float subscapularis;
    private float umbelicale;
    private float femoral;
    private float pi;
    private float bai;
    private float bmi;
    private String notes = "";

    public PatientAntropometryEntity(long patient_ref_ID, float weight, float height, float weist, float belly, float hips, float leg, float arm, float bicipital, float pectoral, float subscapularis, float umbelicale, float femoral, float pi, float bai, float bmi, String notes) {
        this.patient_ref_ID = patient_ref_ID;
        Date date = new Date();
        this.antropometryTime = date;
        this.weight = weight;
        this.height = height;
        this.weist = weist;
        this.belly = belly;
        this.hips = hips;
        this.leg = leg;
        this.arm = arm;
        this.bicipital = bicipital;
        this.pectoral = pectoral;
        this.subscapularis = subscapularis;
        this.umbelicale = umbelicale;
        this.femoral = femoral;
        this.pi = pi;
        this.bai = bai;
        this.bmi = bmi;
        this.notes = notes;
    }

    public long getAntropometryID() {
        return antropometryID;
    }

    public void setAntropometryID(long antropometryID) {
        this.antropometryID = antropometryID;
    }

    public long getPatient_ref_ID() {
        return patient_ref_ID;
    }

    public void setPatient_ref_ID(long patient_ref_ID) {
        this.patient_ref_ID = patient_ref_ID;
    }

    public Date getAntropometryTime() {
        return antropometryTime;
    }

    public void setAntropometryTime(Date antropometryTime) {
        this.antropometryTime = antropometryTime;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWeist() {
        return weist;
    }

    public void setWeist(float weist) {
        this.weist = weist;
    }

    public float getBelly() {
        return belly;
    }

    public void setBelly(float belly) {
        this.belly = belly;
    }

    public float getHips() {
        return hips;
    }

    public void setHips(float hips) {
        this.hips = hips;
    }

    public float getLeg() {
        return leg;
    }

    public void setLeg(float leg) {
        this.leg = leg;
    }

    public float getArm() {
        return arm;
    }

    public void setArm(float arm) {
        this.arm = arm;
    }

    public float getBicipital() {
        return bicipital;
    }

    public void setBicipital(float bicipital) {
        this.bicipital = bicipital;
    }

    public float getPectoral() {
        return pectoral;
    }

    public void setPectoral(float pectoral) {
        this.pectoral = pectoral;
    }

    public float getSubscapularis() {
        return subscapularis;
    }

    public void setSubscapularis(float subscapularis) {
        this.subscapularis = subscapularis;
    }

    public float getUmbelicale() {
        return umbelicale;
    }

    public void setUmbelicale(float umbelicale) {
        this.umbelicale = umbelicale;
    }

    public float getFemoral() {
        return femoral;
    }

    public void setFemoral(float femoral) {
        this.femoral = femoral;
    }

    public float getPi() {
        return pi;
    }

    public void setPi(float pi) {
        this.pi = pi;
    }

    public float getBai() {
        return bai;
    }

    public void setBai(float bai) {
        this.bai = bai;
    }

    public float getBmi() {
        return bmi;
    }

    public void setBmi(float bmi) {
        this.bmi = bmi;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
