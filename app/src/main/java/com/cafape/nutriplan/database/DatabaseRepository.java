package com.cafape.nutriplan.database;

import android.content.Context;

import androidx.room.Room;

import static com.cafape.nutriplan.Globals.DBNAME;

public class DatabaseRepository
{
    private Context context;
    private static DatabaseRepository mInstance;

    //our app database object
    private AppDatabase appDatabase;

    private DatabaseRepository(Context context) {
        this.context = context;

        //creating the app database with Room database builder
        //MyToDos is the name of the database
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, DBNAME).build();
    }

    public static synchronized DatabaseRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new DatabaseRepository(context);
        }
        return mInstance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
