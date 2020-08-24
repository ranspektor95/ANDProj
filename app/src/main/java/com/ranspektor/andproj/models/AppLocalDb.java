package com.ranspektor.andproj.models;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.ranspektor.andproj.ANDApplication;

//When changing db (schemas, properties, methods, etc...) change version number
@Database(entities = {Entry.class}, version = 4)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract EntryDao entryDao();
}

public class AppLocalDb {
    static public AppLocalDbRepository db = Room.databaseBuilder(
            ANDApplication.context,
            AppLocalDbRepository.class,
            "dbDataFile.db")
            .fallbackToDestructiveMigration()
            .build();
}
