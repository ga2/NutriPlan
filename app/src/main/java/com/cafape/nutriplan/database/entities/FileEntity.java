package com.cafape.nutriplan.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.cafape.nutriplan.database.converters.TimestampConverter;

import java.io.Serializable;
import java.util.Date;

@Entity
public class FileEntity implements Serializable
{
    @PrimaryKey(autoGenerate = true)
    private long fileID;
    private String filename;
    @TypeConverters({TimestampConverter.class})
    private Date fileCreationTime;
    private String fileContent;
    private String filePseudoname = "";
    private long patientID_ref;

    public FileEntity(String filename, String fileContent, String filePseudoname, long patientID_ref) {
        this.filename = filename;
        Date date = new Date();
        this.fileCreationTime = date;
        this.fileContent = fileContent;
        this.filePseudoname = filePseudoname;
        this.patientID_ref = patientID_ref;
    }

    public long getFileID() {
        return fileID;
    }

    public void setFileID(long fileID) {
        this.fileID = fileID;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getFileCreationTime() {
        return fileCreationTime;
    }

    public void setFileCreationTime(Date fileCreationTime) {
        this.fileCreationTime = fileCreationTime;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }

    public String getFilePseudoname() {
        return filePseudoname;
    }

    public void setFilePseudoname(String filePseudoname) {
        this.filePseudoname = filePseudoname;
    }

    public long getPatientID_ref() {
        return patientID_ref;
    }

    public void setPatientID_ref(long patientID_ref) {
        this.patientID_ref = patientID_ref;
    }
}
