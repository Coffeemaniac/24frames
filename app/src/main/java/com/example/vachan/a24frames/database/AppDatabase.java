package com.example.vachan.a24frames.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Movies.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

    private static final Object Lock = new Object();
    private static AppDatabase sInstance;


    public static AppDatabase getInstance(final Context context) {
        if (sInstance == null) {
            synchronized (Lock) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "movies_database")
                            .build();

                }
            }
        }
        return sInstance;
    }

}
