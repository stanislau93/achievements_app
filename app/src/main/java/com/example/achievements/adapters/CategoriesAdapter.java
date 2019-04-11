package com.example.achievements.adapters;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.achievements.R;
import com.example.achievements.data.Category;

import java.util.ArrayList;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.CategoriesViewHolder> {

    private ArrayList<Category> dataset;

    private float dpConversionRatio;

    private View.OnClickListener deletionClickListener;

    public static class CategoriesViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout;

        public CategoriesViewHolder(@NonNull LinearLayout ll) {
            super(ll);
            layout = ll;
        }

        public View getEmptyStaticBarView()
        {
            View view = new View(layout.getContext());
            view.setBackgroundResource(R.drawable.statc_bar_empty);

            view.setId(View.generateViewId());

            return view;
        }

        public View getFilledStaticBarView()
        {
            View view = new View(layout.getContext());
            view.setBackgroundResource(R.drawable.static_bar_filling);

            view.setId(View.generateViewId());

            return view;
        }
    }

    public CategoriesAdapter(ArrayList<Category> dataset, float dpConversionRatio, View.OnClickListener deletionClickListener) {
        this.dpConversionRatio = dpConversionRatio;
        this.dataset = dataset;
        this.deletionClickListener = deletionClickListener;
    }

    @NonNull
    @Override
    public CategoriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LinearLayout ll = (LinearLayout) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item_layout, parent, false);

        return new CategoriesViewHolder(ll);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesViewHolder holder, int position) {
        TextView titleView = holder.layout.findViewById(R.id.category_title);
        TextView lvlTextView = holder.layout.findViewById(R.id.textview_level);
        TextView requestedExpTextView = holder.layout.findViewById(R.id.textview_requested_exp);

        Category currentCat = dataset.get(position);

        String colorString = currentCat.color;
        String title = currentCat.title;
        int level = currentCat.level;
        int experience = currentCat.experience;
        int requiredExp = currentCat.experienceRequired;
        final long catId = currentCat.id;

        float percentage = (float)experience / (float)requiredExp;

        RelativeLayout subLayout = holder.layout.findViewById(R.id.rel_layout_experience_bar);

        View emptyStaticBarView = holder.getEmptyStaticBarView();
        int newWidth = (int)(Math.round(270*dpConversionRatio) * percentage);
        View filledStaticBarView = holder.getFilledStaticBarView();

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                (int)Math.round(270*dpConversionRatio), 20);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);

        subLayout.addView(emptyStaticBarView, params);

        params = new RelativeLayout.LayoutParams(
                newWidth, 20);
        subLayout.addView(filledStaticBarView, params);

        holder.layout.setBackgroundColor(Color.parseColor(colorString));
        titleView.setText(title);
        lvlTextView.setText(String.valueOf(level));

        String requestedExpString = String.valueOf(experience) + "\\" + String.valueOf(requiredExp);

        requestedExpTextView.setText(requestedExpString);

        ImageButton deleteCategoryButton = holder.layout.findViewById(R.id.removeCategory);
        deleteCategoryButton.setId((int)catId);
        deleteCategoryButton.setOnClickListener(deletionClickListener);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }
}
