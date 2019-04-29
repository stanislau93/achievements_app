package com.example.achievements.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.achievements.R;
import com.example.achievements.data.AppDatabase;

public class DeleteQuestConfirmationFragment extends BaseDialogFragment {
    private AppDatabase db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_confirm_quest_deletion, null);
        final long questID = getArguments().getLong("quest_id");

        db = AppDatabase.getInstance(getContext());

        Button deleteConfirmationButton = v.findViewById(R.id.button_confirm_quest_deletion);
        Button dismissDeletionButton = v.findViewById(R.id.button_dismiss_quest_deletion);

        deleteConfirmationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.beginTransaction();
                db.quest().deleteById(questID);
                db.setTransactionSuccessful();
                db.endTransaction();

                dismiss();
            }
        });

        dismissDeletionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return v;
    }
}
