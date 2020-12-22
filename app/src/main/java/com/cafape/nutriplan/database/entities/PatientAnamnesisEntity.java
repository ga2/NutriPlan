package com.cafape.nutriplan.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class PatientAnamnesisEntity implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    private long anamnesisID;
    private long patient_ref_ID;
    private String welcomefood = "";
    private String nonwelcomefood = "";
    private String anamnesis_breakfast = "";
    private String anamnesis_nibble = "";
    private String anamnesis_lunch = "";
    private String anamnesis_snack = "";
    private String anamnesis_dinner = "";
    private String anamnesis_postdinner = "";
    private String prototype_breakfast = "";
    private String prototype_nibble = "";
    private String prototype_lunch = "";
    private String prototype_snack = "";
    private String prototype_dinner = "";

    public PatientAnamnesisEntity(long patient_ref_ID, String welcomefood, String nonwelcomefood, String anamnesis_breakfast, String anamnesis_nibble, String anamnesis_lunch, String anamnesis_snack, String anamnesis_dinner, String anamnesis_postdinner, String prototype_breakfast, String prototype_nibble, String prototype_lunch, String prototype_snack, String prototype_dinner) {
        this.patient_ref_ID = patient_ref_ID;
        this.welcomefood = welcomefood;
        this.nonwelcomefood = nonwelcomefood;
        this.anamnesis_breakfast = anamnesis_breakfast;
        this.anamnesis_nibble = anamnesis_nibble;
        this.anamnesis_lunch = anamnesis_lunch;
        this.anamnesis_snack = anamnesis_snack;
        this.anamnesis_dinner = anamnesis_dinner;
        this.anamnesis_postdinner = anamnesis_postdinner;
        this.prototype_breakfast = prototype_breakfast;
        this.prototype_nibble = prototype_nibble;
        this.prototype_lunch = prototype_lunch;
        this.prototype_snack = prototype_snack;
        this.prototype_dinner = prototype_dinner;
    }

    public long getAnamnesisID() {
        return anamnesisID;
    }

    public void setAnamnesisID(long anamnesisID) {
        this.anamnesisID = anamnesisID;
    }

    public long getPatient_ref_ID() {
        return patient_ref_ID;
    }

    public void setPatient_ref_ID(long patient_ref_ID) {
        this.patient_ref_ID = patient_ref_ID;
    }

    public String getWelcomefood() {
        return welcomefood;
    }

    public void setWelcomefood(String welcomefood) {
        this.welcomefood = welcomefood;
    }

    public String getNonwelcomefood() {
        return nonwelcomefood;
    }

    public void setNonwelcomefood(String nonwelcomefood) {
        this.nonwelcomefood = nonwelcomefood;
    }

    public String getAnamnesis_breakfast() {
        return anamnesis_breakfast;
    }

    public void setAnamnesis_breakfast(String anamnesis_breakfast) {
        this.anamnesis_breakfast = anamnesis_breakfast;
    }

    public String getAnamnesis_nibble() {
        return anamnesis_nibble;
    }

    public void setAnamnesis_nibble(String anamnesis_nibble) {
        this.anamnesis_nibble = anamnesis_nibble;
    }

    public String getAnamnesis_lunch() {
        return anamnesis_lunch;
    }

    public void setAnamnesis_lunch(String anamnesis_lunch) {
        this.anamnesis_lunch = anamnesis_lunch;
    }

    public String getAnamnesis_snack() {
        return anamnesis_snack;
    }

    public void setAnamnesis_snack(String anamnesis_snack) {
        this.anamnesis_snack = anamnesis_snack;
    }

    public String getAnamnesis_dinner() {
        return anamnesis_dinner;
    }

    public void setAnamnesis_dinner(String anamnesis_dinner) {
        this.anamnesis_dinner = anamnesis_dinner;
    }

    public String getAnamnesis_postdinner() {
        return anamnesis_postdinner;
    }

    public void setAnamnesis_postdinner(String anamnesis_postdinner) {
        this.anamnesis_postdinner = anamnesis_postdinner;
    }

    public String getPrototype_breakfast() {
        return prototype_breakfast;
    }

    public void setPrototype_breakfast(String prototype_breakfast) {
        this.prototype_breakfast = prototype_breakfast;
    }

    public String getPrototype_nibble() {
        return prototype_nibble;
    }

    public void setPrototype_nibble(String prototype_nibble) {
        this.prototype_nibble = prototype_nibble;
    }

    public String getPrototype_lunch() {
        return prototype_lunch;
    }

    public void setPrototype_lunch(String prototype_lunch) {
        this.prototype_lunch = prototype_lunch;
    }

    public String getPrototype_snack() {
        return prototype_snack;
    }

    public void setPrototype_snack(String prototype_snack) {
        this.prototype_snack = prototype_snack;
    }

    public String getPrototype_dinner() {
        return prototype_dinner;
    }

    public void setPrototype_dinner(String prototype_dinner) {
        this.prototype_dinner = prototype_dinner;
    }
}
