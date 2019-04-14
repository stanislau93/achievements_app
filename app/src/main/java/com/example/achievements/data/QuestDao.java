package com.example.achievements.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.database.Cursor;

@Dao
public abstract class QuestDao {
    @Query("SELECT * FROM " + Quest.TABLE_NAME)
    public abstract Cursor selectAll();

    @Insert()
    public abstract long insert(Quest object);

    @Insert()
    public abstract long[] insertMany(Quest[] object);

    @Query("DELETE FROM " + Quest.TABLE_NAME + " WHERE id = :id")
    public abstract int deleteById(long id);
}
