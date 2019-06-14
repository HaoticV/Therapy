package com.example.therapy.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DaoAccess {

    @Insert
    void insertOnlySingleDrug(Drug drug);

    @Insert
    void insertMultipleDrugs(List<Drug> drugsList);

    @Query("SELECT * FROM drug WHERE drugId = :drugId")
    Drug fetchOneDrugByDrugId(int drugId);

    @Query("SELECT * FROM drug WHERE time = :time")
    List<Drug> fetchDrugsByTime(String time);

    @Query("SELECT * FROM drug WHERE name LIKE :name LIMIT 1")
    Drug findByName(String name);

    @Query("SELECT * FROM drug ORDER BY time ASC")
    List<Drug> findAllDrugs();

    @Query("SELECT COUNT(drugId) FROM drug")
    int size();

    @Query("SELECT MAX(drugId) FROM drug")
    int maxId();

    @Update
    void updateDrug(Drug drug);

    @Delete
    void deleteDrug(Drug drug);
}

