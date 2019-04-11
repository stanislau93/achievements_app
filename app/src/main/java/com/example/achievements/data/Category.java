package com.example.achievements.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Entity;

@Entity(tableName = Category.TABLE_NAME, indices = {@Index(value = "title", unique = true)})
public class Category {
    public static final String TABLE_NAME = "categories";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = "id")
    public long id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "level")
    public int level;

    @ColumnInfo(name = "experience")
    public int experience;

    /**
     * Experience required to reach the next level in this category
     */
    @ColumnInfo(name = "experience_required")
    public int experienceRequired;

    @ColumnInfo(name = "color")
    public String color;
}