package com.example.achievements.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.Nullable;

import java.util.Date;

@Entity(tableName = Quest.TABLE_NAME, indices = {@Index(value = "title", unique = true)})
public class Quest {
    static final String TABLE_NAME = "quests";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(index = true, name = "id")
    public long id;

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "description")
    public String description;

    @ColumnInfo(name = "reward")
    public String reward;

    @ColumnInfo(name = "deadline")
    @Nullable
    @TypeConverters(DateConverter.class)
    public Date deadline;

    @ColumnInfo(name = "category_id")
    @ForeignKey(entity = Category.class, parentColumns = {"id"}, childColumns = {"category_id"})
    public long categoryID;

    @ColumnInfo(name = "difficulty")
    @TypeConverters(DifficultyConverter.class)
    public int difficulty;

    @ColumnInfo(name = "status")
    @TypeConverters(StatusConverter.class)
    public int status;

    public enum Difficulty {
        EASY(0),
        MEDIUM(1),
        HARD(2);

        private int code;

        Difficulty(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public enum Status {
        DOING(0),
        DONE(1),
        REWARDED(2);

        private int code;

        Status(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }
}

class DateConverter {

    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null: new Date(dateLong);
    }

    @TypeConverter
    public static Long fromDate(Date date){
        return date == null ? null : date.getTime();
    }
}

class DifficultyConverter {

    @TypeConverter
    public static Quest.Difficulty toDifficulty(int difficulty) {
        if (difficulty == Quest.Difficulty.EASY.getCode()) {
            return Quest.Difficulty.EASY;
        } else if (difficulty == Quest.Difficulty.MEDIUM.getCode()) {
            return Quest.Difficulty.MEDIUM;
        } else if (difficulty == Quest.Difficulty.HARD.getCode()) {
            return Quest.Difficulty.HARD;
        } else {
            throw new IllegalArgumentException("Could not map value");
        }
    }

    @TypeConverter
    public static int toInt(Quest.Difficulty difficulty) {
        return difficulty.getCode();
    }
}

class StatusConverter {

    @TypeConverter
    public static Quest.Status toStatus(int status) {
        if (status == Quest.Status.DOING.getCode()) {
            return Quest.Status.DOING;
        } else if (status == Quest.Status.DONE.getCode()) {
            return Quest.Status.DONE;
        } else if (status == Quest.Status.REWARDED.getCode()) {
            return Quest.Status.REWARDED;
        } else {
            throw new IllegalArgumentException("Could not map value");
        }
    }

    @TypeConverter
    public static int toInt(Quest.Status status) {
        return status.getCode();
    }
}
