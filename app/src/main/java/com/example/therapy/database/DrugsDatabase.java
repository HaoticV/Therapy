package com.example.therapy.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Drug.class}, version = 8, exportSchema = false)
public abstract class DrugsDatabase extends RoomDatabase{

    public abstract DaoAccess daoAccess();

    private static final String DATABASE_NAME = "drugs_db";

    private static DrugsDatabase INSTANCE;

    public static DrugsDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), DrugsDatabase.class, DATABASE_NAME)
                            .allowMainThreadQueries().fallbackToDestructiveMigration()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}