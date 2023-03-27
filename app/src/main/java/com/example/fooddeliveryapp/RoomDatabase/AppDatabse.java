package com.example.fooddeliveryapp.RoomDatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = {Cartmodel.class} , version = 1 , exportSchema = false)
public abstract class AppDatabse extends RoomDatabase {

    public abstract UserDao userDao();

    private static AppDatabse Instance;

    public static AppDatabse getAppDataBase(Context context){

        if(Instance == null){
            Instance = Room.databaseBuilder(context.getApplicationContext(),AppDatabse.class,"DB_NAME")
                    .allowMainThreadQueries()
                    .build();
        }
        return Instance;
    }

}
