package com.example.therapy.database;

import android.arch.persistence.room.*;
import android.content.Context;

@Database(entities = {Drugs.class}, version = 1, exportSchema = false)
public abstract class DrugsDatabase extends RoomDatabase{

    public abstract DaoAccess daoAccess();

    private static final String DATABASE_NAME = "drugs_db";

    private static DrugsDatabase INSTANCE;

    public static DrugsDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.inMemoryDatabaseBuilder(context.getApplicationContext(), DrugsDatabase.class)
                            .allowMainThreadQueries()
                            .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

}