package com.example.achievements.data;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import android.database.Cursor;

@Dao
public abstract class CategoryDao {
    @Query("SELECT * FROM " + Category.TABLE_NAME)
    public abstract Cursor selectAll();

    @Insert()
    public abstract long insert(Category object);

    @Insert()
    public abstract long[] insertMany(Category[] object);

    @Query("DELETE FROM " + Category.TABLE_NAME + " WHERE id = :id")
    public abstract int deleteById(long id);
}
