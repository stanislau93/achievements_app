package com.example.achievements.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
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
import com.example.achievements.dialogs.CreateCategoryDialogFragment;
import com.example.achievements.dialogs.DeleteCategoryConfirmationFragment;
import com.example.achievements.dialogs.DeleteQuestConfirmationFragment;

import java.security.InvalidParameterException;
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

        View.OnClickListener deletionClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("quest_id", v.getId());
                openDialog("delete_quest", bundle);
            }
        };

        adapter = new QuestsAdapter(dataset, db, deletionClickListener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void openDialog(String type, Bundle bundle) {
        android.support.v4.app.FragmentManager manager = getActivity().getSupportFragmentManager();
        Fragment oldFragment = manager.findFragmentByTag(type);
        DialogFragment fragment;

        if (oldFragment != null) {
            manager.beginTransaction().remove(oldFragment).commit();
        }

        DialogInterface.OnDismissListener onDismissListener = new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                loadQuestsList();
            }
        };

        switch (type) {
            case "delete_quest":
                fragment = new DeleteQuestConfirmationFragment();
                fragment.setArguments(bundle);
                fragment.show(manager, type);
                ((DeleteQuestConfirmationFragment) fragment).setOnDismissListener(onDismissListener);
                break;
            /*case "create_quest":
                fragment = new CreateCategoryDialogFragment();
                fragment.setArguments(bundle);
                fragment.show(manager, type);
                ((CreateCategoryDialogFragment) fragment).setOnDismissListener(onDismissListener);
                break;*/
            default:
                throw new InvalidParameterException("Unsupported fragment type!");
        }
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
        db.quest().deleteById(1);
        db.quest().deleteById(2);
        db.quest().deleteById(3);
        db.category().deleteById(1);
        db.category().deleteById(2);
        Category[] test_cat_dataset = {
                new Category() {{ id=1; title="Кулинария"; color="#AA2323"; }},
                new Category() {{ id=2; title="Спорт"; color="#4422BA"; }}
        };

        Quest[] test_dataset = {
                new Quest() {{ id=1; title="Приготовить лазанью"; description="easy"; difficulty=0; status=0; reward="2 часа игр"; deadline=(new Date());categoryID=1;}},
                new Quest() {{ id=2; title="40 отжиманий за раз"; description="normal"; difficulty=1; status=1; reward="завтрак в кафе"; deadline=(new Date());categoryID=2;}},
                new Quest() {{ id=3; title="Замесить тесто"; description="hard"; difficulty=2; status=2; reward="Купить чипсов"; deadline=(new Date());categoryID=1;}}
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
