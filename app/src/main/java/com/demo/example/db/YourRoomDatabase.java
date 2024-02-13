package com.demo.example.db;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.demo.example.model.Converters;
import com.demo.example.model.YourDataModel;

@Database(entities = {YourDataModel.class}, version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class YourRoomDatabase extends RoomDatabase {

    private static volatile YourRoomDatabase INSTANCE;

    public abstract YourDao yourDao();

    public static YourRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (YourRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), YourRoomDatabase.class, "your_database_name").build();
                }
            }
        }
        return INSTANCE;
    }
}