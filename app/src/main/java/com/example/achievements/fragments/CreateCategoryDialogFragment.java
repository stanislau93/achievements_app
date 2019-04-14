package com.example.achievements.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.achievements.R;
import com.example.achievements.data.AppDatabase;
import com.example.achievements.data.Category;
import com.flask.colorpicker.ColorPickerView;

public class CreateCategoryDialogFragment extends DialogFragment {

    final int EXP_REQ_FOR_SECOND_LVL = 1000;

    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View dialogView = inflater.inflate(R.layout.fragment_create_category, null);
        db = AppDatabase.getInstance(getContext());

        final ColorPickerView colorPickerView = dialogView.findViewById(R.id.color_picker_view);

        Button submitButton = dialogView.findViewById(R.id.button_confirm_category_creation);
        Button cancelButton = dialogView.findViewById(R.id.button_dismiss_category_creation);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int pickedColor = colorPickerView.getSelectedColor();
                final EditText nameView = dialogView.findViewById(R.id.category_creation_name);

                Category cat = new Category(){{
                        title = nameView.getText().toString();
                        level = 1;
                        experience = 0;
                        experienceRequired = EXP_REQ_FOR_SECOND_LVL;
                        color = String.format("#%06X", (0xFFFFFF & pickedColor));
                }};

                storeCategory(cat);

                Log.d("DEBUG", String.format("#%06X", (0xFFFFFF & pickedColor)));

                getActivity().finish();
                startActivity(getActivity().getIntent());
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return dialogView;
    }

    private void storeCategory(Category category) {
        db.beginTransaction();
        db.category().insert(category);
        db.setTransactionSuccessful();
        db.endTransaction();
    }
}
