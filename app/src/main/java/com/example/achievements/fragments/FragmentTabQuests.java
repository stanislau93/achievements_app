package com.example.achievements.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.achievements.R;
import com.example.achievements.data.AppDatabase;
import com.example.achievements.data.Category;
import com.example.achievements.data.Quest;

import java.util.Date;

public class FragmentTabQuests extends Fragment {
    View fragment_view;
    private AppDatabase db;
    private Activity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment_view = inflater.inflate(R.layout.fragment_tab_quests, null);
        activity = getActivity();

        db = AppDatabase.getInstance(activity);
        insertFixtureDataset();

        return fragment_view;
    }

    /*TODO - remove*/
    private void insertFixtureDataset()
    {
        Quest[] test_dataset = {
                new Quest() {{ title="quest1"; description="easy"; difficulty=0; status=0; reward="pony"; deadline=(new Date());categoryID=1;}},
                new Quest() {{ title="quest2"; description="normal"; difficulty=1; status=1; reward="wendy"; deadline=(new Date());categoryID=2;}},
                new Quest() {{ title="quest3"; description="hard"; difficulty=2; status=2; reward="candy"; deadline=(new Date());categoryID=3;}}
        };

        Cursor qq = db.quest().selectAll();

        if (!qq.moveToNext()) {
            db.quest().insertMany(test_dataset);
        }
    }
}
