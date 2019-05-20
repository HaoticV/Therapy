package com.example.therapy.database;

import android.arch.persistence.room.*;
import android.support.annotation.NonNull;

@Entity
public class Drugs {
    @NonNull
    @PrimaryKey
    private int drugId;
    @ColumnInfo(name = "nazwa")
    private String drugName;
    @ColumnInfo(name = "opis")
    private String drugDescription;

    public Drugs(int drugId, String drugName, String drugDescription) {
        this.drugId = drugId;
        this.drugName = drugName;
        this.drugDescription = drugDescription;
    }

    @NonNull
    public int getDrugId() {
        return drugId;
    }

    public void setDrugId(@NonNull int drugId) {
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
