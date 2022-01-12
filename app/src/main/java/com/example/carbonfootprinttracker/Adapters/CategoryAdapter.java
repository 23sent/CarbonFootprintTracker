package com.example.carbonfootprinttracker.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.example.carbonfootprinttracker.EmissionTypes;
import com.example.carbonfootprinttracker.R;

public class CategoryAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private EmissionTypes.Category[] categories;
    private CategoryAdapterListener listener;
    public CategoryAdapter(Activity activity, EmissionTypes.Category[] categories, CategoryAdapterListener listener) {
        this.mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.categories = categories;
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return categories.length;
    }

    @Override
    public Object getItem(int i) {
        return categories[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        EmissionTypes.Category category = categories[i];

        Button rowView;
        rowView = (Button) mInflater.inflate(R.layout.listview_button, null);
        rowView.setText(category.fullName);
        rowView.setOnClickListener((View mView) -> {
            listener.onClickCategory(category);
        });
        return rowView;
    }

    public interface CategoryAdapterListener {
        void onClickCategory(EmissionTypes.Category category);
    }
}
