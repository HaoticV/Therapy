package com.example.therapy.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "drug")
public class Drug implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int drugId;
    @ColumnInfo(name = "name")
    private String drugName;
    @ColumnInfo(name = "image")
    private String drugImagePath;
    @ColumnInfo(name = "description")
    private String drugDescription;
    @ColumnInfo(name = "time")
    private String time;

    public Drug() {
    }

    @Ignore
    public Drug(String drugName, String drugImagePath) {
        this.drugName = drugName;
        this.drugImagePath = drugImagePath;
    }

    public String getDrugImagePath() {
        return drugImagePath;
    }

    public void setDrugImagePath(String drugImagePath) {
        this.drugImagePath = drugImagePath;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(int drugId) {
        this.drugId = drugId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public String getDrugDescription() {
        return drugDescription;
    }

    public void setDrugDescription(String drugDescription) {
        this.drugDescription = drugDescription;
    }


}

