package com.ranspektor.andproj.models;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EntryDao {
    @Query("select * from Entry")
    LiveData<List<Entry>> getAllEntries();

    @Query("select * from Entry where userId like :userId")
    LiveData<List<Entry>> getUserEntries(String userId);

    @Query("select * from Entry where id like :id")
    LiveData<Entry> getEntry(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Entry... entries);

    @Delete
    void delete(Entry entry);
}
