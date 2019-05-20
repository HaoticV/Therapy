package com.example.therapy.database;

import android.arch.persistence.room.*;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    void insertOnlySingleDrug (Drugs drugs);
    @Insert
    void insertMultipleDrugs (List<Drugs> drugsList);
    @Query ("SELECT * FROM Drugs WHERE drugId = :drugId")
    Drugs fetchOneDrugbyDrugId (int drugId);
    @Update
    void updateDrug (Drugs drugs);
    @Delete
    void deleteDrug (Drugs drugs);
}

