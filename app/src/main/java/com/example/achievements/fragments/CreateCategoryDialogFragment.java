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

import com.example.achievements.R;
import com.flask.colorpicker.ColorPickerView;

public class CreateCategoryDialogFragment extends DialogFragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create_category, null);

        final ColorPickerView colorPickerView = v.findViewById(R.id.color_picker_view);

        Button submitButton = v.findViewById(R.id.button_confirm_category_creation);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = colorPickerView.getSelectedColor();
                v.setBackgroundColor(color);

                Log.d("DEBUG", String.format("#%06X", (0xFFFFFF & color)));
            }
        });

        return v;
    }
}
