package com.example.achievements.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.achievements.R;
import com.example.achievements.data.AppDatabase;
import com.example.achievements.data.Quest;

import java.util.ArrayList;

public class QuestsAdapter extends RecyclerView.Adapter<QuestsAdapter.QuestsViewHolder> {
    private ArrayList<Quest[]> dataset;
    private AppDatabase db;

    public QuestsAdapter(ArrayList<Quest> dataset, AppDatabase db) {
        this.db = db;
        this.dataset = new ArrayList<>();
        Quest[] row = new Quest[2];

        for (int i = 0; i < dataset.size(); i++) {
            if (i == 0 || i % 2 == 0) {
                row[0] = dataset.get(i);

                if (i == dataset.size() - 1) {
                    this.dataset.add(row.clone());
                }
            } else {
                row[1] = dataset.get(i);
                this.dataset.add(row.clone());
                row = new Quest[2];

            }
        }
    }

    public static class QuestsViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;

        public QuestsViewHolder(@NonNull LinearLayout ll) {
            super(ll);
            layout = ll;
        }
    }

    @NonNull
    @Override
    public QuestsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout ll = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quest_item_layout, parent, false);

        return new QuestsViewHolder(ll);
    }

    @Override
    public void onBindViewHolder(@NonNull QuestsViewHolder viewHolder, int i) {
        TextView titleSub1 = viewHolder.layout.findViewById(R.id.quest_title_sub_1);
        TextView titleSub2 = viewHolder.layout.findViewById(R.id.quest_title_sub_2);

        Quest[] row = dataset.get(i);

        String title = row[0].title;
        long categoryID1 = row[0].categoryID;

        String color1 = db.category().selectColorById(categoryID1);

        LinearLayout sublayout1 = viewHolder.layout.findViewById(R.id.rec_view_quests_sub_1);
        LinearLayout sublayout2 = viewHolder.layout.findViewById(R.id.rec_view_quests_sub_2);

        titleSub1.setText(title);

        sublayout1.setBackgroundColor(Color.parseColor(color1));

        if (row[1] != null) {
            title = row[1].title;
            titleSub2.setText(title);

            long categoryID2 = row[1].categoryID;
            String color2 = db.category().selectColorById(categoryID2);

            sublayout2.setBackgroundColor(Color.parseColor(color2));
        } else {
            sublayout2.setVisibility(View.INVISIBLE);
        }

    }


    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
