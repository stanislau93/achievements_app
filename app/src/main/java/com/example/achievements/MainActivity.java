package com.example.achievements;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageButton;

import com.example.achievements.adapters.CategoriesAdapter;
import com.example.achievements.data.AppDatabase;
import com.example.achievements.data.Category;
import com.example.achievements.fragments.CreateCategoryDialogFragment;
import com.example.achievements.fragments.DeleteCategoryConfirmationFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;

    private AppDatabase db;

    /**
     * TODO - Replace by actual dataset from the database!
     */
    private Category[] testDataset = {
            new Category() {{title="cat1"; experience=500; experienceRequired=1000; color="#BB3333"; level=3;}},
            new Category() {{title="cat2"; experience=700; experienceRequired=1000; color="#44AA22"; level=10;}},
            new Category() {{title="cat3"; experience=300; experienceRequired=1000; color="#3344BB"; level=5;}}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.main_screen).setBackgroundColor(
                getResources().getColor(R.color.appBackgroundColor)
        );

        db = AppDatabase.getInstance(this);
        Cursor cc = db.category().selectAll();

        if (!cc.moveToNext()) {
            db.category().insertMany(testDataset);
        }

        recyclerView = findViewById(R.id.rec_view_categories);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);

        float dpConversionRatio = getResources().getDisplayMetrics().xdpi/DisplayMetrics.DENSITY_DEFAULT;

        ArrayList<Category> $dataset = getCategoriesDataset();

        View.OnClickListener deletionClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategoryDeletionDialog(v.getId());
            }
        };

        adapter = new CategoriesAdapter($dataset, dpConversionRatio, deletionClickListener);
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

    protected void setAddCategoryButtonListener()
    {
        ImageButton button = findViewById(R.id.createCategory);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCategoryCreationDialog();
            }
        });
    }

    //Todo - overwrite, DRY!
    protected void openCategoryCreationDialog() {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();

        Fragment oldFragment = manager.findFragmentByTag("fragment_create_category");

        if (oldFragment != null) {
            manager.beginTransaction().remove(oldFragment).commit();
        }

        CreateCategoryDialogFragment fragment = new CreateCategoryDialogFragment();
        fragment.show(manager, "fragment_create_category");
    }

    protected void openCategoryDeletionDialog(long categoryID) {
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();

        Fragment oldFragment = manager.findFragmentByTag("fragment_confirm_category_deletion");

        if (oldFragment != null) {
            manager.beginTransaction().remove(oldFragment).commit();
        }

        DeleteCategoryConfirmationFragment fragment = new DeleteCategoryConfirmationFragment();

        Bundle bundle = new Bundle();
        bundle.putLong("category_id", categoryID);
        fragment.setArguments(bundle);

        fragment.show(manager, "fragment_confirm_category_deletion");
    }
}
