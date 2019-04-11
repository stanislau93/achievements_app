package com.example.achievements.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

@Database(entities = {Category.class,}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    /**
     * @return The DAO for the `categories` table.
     */
    @SuppressWarnings("WeakerAccess")
    public abstract CategoryDao category();

    private static final Category[] PREPOPULATE_DATA = {
            new Category() {{title="cat1"; experience=500; experienceRequired=1000; color="#BB3333"; level=3;}},
            new Category() {{title="cat2"; experience=700; experienceRequired=1000; color="#44AA22"; level=10;}},
            new Category() {{title="cat3"; experience=300; experienceRequired=1000; color="#3344BB"; level=5;}}
    };

    /**
     * @param context The context.
     * @return The singleton instance of AppDatabase.
     */
    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "ex")
                    .addCallback(new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                        }
                    })
                    .allowMainThreadQueries()
                    //.fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    /**
     * Switches the internal implementation with an empty in-memory database.
     *
     * @param context The context.
     */
    @VisibleForTesting
    public static void switchToInMemory(Context context) {
        instance = Room.inMemoryDatabaseBuilder(context.getApplicationContext(),
                AppDatabase.class).build();
    }
}
