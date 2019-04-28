package com.example.achievements.fragments;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.achievements.R;
import com.example.achievements.adapters.QuestsAdapter;
import com.example.achievements.data.AppDatabase;
import com.example.achievements.data.Category;
import com.example.achievements.data.Quest;

import java.util.ArrayList;
import java.util.Date;

public class FragmentTabQuests extends Fragment {
    View fragment_view;
    private AppDatabase db;
    private Activity activity;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragment_view = inflater.inflate(R.layout.fragment_tab_quests, null);
        initializeFields();
        insertFixtureDataset();
        loadQuestsList();
        return fragment_view;
    }

    private void initializeFields()
    {
        activity = getActivity();
        db = AppDatabase.getInstance(activity);

        recyclerView = fragment_view.findViewById(R.id.rec_view_quests);
        layoutManager = new LinearLayoutManager(activity);
    }

    private void loadQuestsList()
    {
        ArrayList<Quest> dataset = getQuestsDataset();

        adapter = new QuestsAdapter(dataset, db);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    protected ArrayList<Quest> getQuestsDataset()
    {
        final Cursor cursor = db.quest().selectAll();
        final ArrayList<Quest> quests = new ArrayList<>();

        while (cursor.moveToNext()) {
            quests.add(new Quest(){{
                title = cursor.getString(cursor.getColumnIndex("title"));
                id = cursor.getLong(cursor.getColumnIndex("id"));
                description = cursor.getString(cursor.getColumnIndex("description"));
                reward = cursor.getString(cursor.getColumnIndex("reward"));
                deadline = new Date(cursor.getLong(cursor.getColumnIndex("deadline")));
                categoryID = cursor.getLong(cursor.getColumnIndex("category_id"));
                difficulty = cursor.getInt(cursor.getColumnIndex("difficulty"));
                status = cursor.getInt(cursor.getColumnIndex("status"));
            }});
        }

        return quests;
    }

    /*TODO - remove*/
    private void insertFixtureDataset()
    {
        Category[] test_cat_dataset = {
                new Category() {{ id=1; title="test1"; color="#AA2323"; }},
                new Category() {{ id=2; title="test2"; color="#4422BA"; }}
        };

        Quest[] test_dataset = {
                new Quest() {{ id=1; title="quest1"; description="easy"; difficulty=0; status=0; reward="pony"; deadline=(new Date());categoryID=1;}},
                new Quest() {{ id=2; title="quest2"; description="normal"; difficulty=1; status=1; reward="wendy"; deadline=(new Date());categoryID=2;}},
                new Quest() {{ id=3; title="quest3"; description="hard"; difficulty=2; status=2; reward="candy"; deadline=(new Date());categoryID=1;}}
        };

        Cursor cc = db.category().selectAll();
        Cursor qq = db.quest().selectAll();

        if (!qq.moveToNext()) {
            db.quest().insertMany(test_dataset);
        }

        if (!cc.moveToNext()) {
            db.category().insertMany(test_cat_dataset);
        }
    }
}
