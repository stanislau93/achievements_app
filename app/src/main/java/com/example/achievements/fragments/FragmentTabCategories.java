package com.example.achievements.fragments;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.achievements.R;
import com.example.achievements.adapters.CategoriesAdapter;
import com.example.achievements.data.AppDatabase;
import com.example.achievements.data.Category;
import com.example.achievements.dialogs.CreateCategoryDialogFragment;
import com.example.achievements.dialogs.DeleteCategoryConfirmationFragment;

import java.security.InvalidParameterException;
import java.util.ArrayList;

public class FragmentTabCategories extends Fragment {
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private AppDatabase db;

    private View fragment_view;

    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragment_view = inflater.inflate(R.layout.fragment_tab_categories, container, false);

        fragment_view.findViewById(R.id.main_screen).setBackgroundColor(
                getResources().getColor(R.color.appBackgroundColor)
        );

        initializeFields();
        loadCategoryList();

        return fragment_view;
    }

    private void initializeFields()
    {
        activity = getActivity();
        db = AppDatabase.getInstance(activity);
        recyclerView = fragment_view.findViewById(R.id.rec_view_categories);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(activity);
    }

    protected void setAddCategoryButtonListener()
    {
        ImageButton button = fragment_view.findViewById(R.id.createCategory);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("parent_fragment_tag", getTag());
                openDialog("create_cat", bundle);
            }
        });
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
                loadCategoryList();
            }
        };

        switch (type) {
            case "delete_cat":
                fragment = new DeleteCategoryConfirmationFragment();
                fragment.setArguments(bundle);
                fragment.show(manager, type);
                ((DeleteCategoryConfirmationFragment) fragment).setOnDismissListener(onDismissListener);
                break;
            case "create_cat":
                fragment = new CreateCategoryDialogFragment();
                fragment.setArguments(bundle);
                fragment.show(manager, type);
                ((CreateCategoryDialogFragment) fragment).setOnDismissListener(onDismissListener);
                break;
            default:
                throw new InvalidParameterException("Unsupported fragment type!");
        }
    }

    private void loadCategoryList()
    {
        float dpConversionRatio = getResources().getDisplayMetrics().xdpi/ DisplayMetrics.DENSITY_DEFAULT;

        ArrayList<Category> dataset = getCategoriesDataset();

        View.OnClickListener deletionClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putLong("category_id", v.getId());
                openDialog("delete_cat", bundle);
            }
        };

        adapter = new CategoriesAdapter(dataset, dpConversionRatio, deletionClickListener);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        setAddCategoryButtonListener();
    }

    protected ArrayList<Category> getCategoriesDataset()
    {
        final Cursor catsCursor = db.category().selectAll();
        final ArrayList<Category> categories = new ArrayList<>();

        while (catsCursor.moveToNext()) {
            categories.add(new Category(){{
                title = catsCursor.getString(catsCursor.getColumnIndex("title"));
                id = catsCursor.getLong(catsCursor.getColumnIndex("id"));
                experience = catsCursor.getInt(catsCursor.getColumnIndex("experience"));
                experienceRequired = catsCursor.getInt(catsCursor.getColumnIndex("experience_required"));
                color = catsCursor.getString(catsCursor.getColumnIndex("color"));
                level = catsCursor.getInt(catsCursor.getColumnIndex("level"));
            }});
        }

        return categories;
    }
}
