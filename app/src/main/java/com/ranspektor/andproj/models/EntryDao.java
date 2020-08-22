package com.ranspektor.andproj.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EntryDao {
    @Query("select * from Entry")
    List<Entry> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Entry... entries);

    @Delete
    void delete(Entry entry);

    //TODO: add getters

}
