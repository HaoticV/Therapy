package com.example.therapy.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "drug")
public class Drug {
    @PrimaryKey(autoGenerate = true)
    @NonNull
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

