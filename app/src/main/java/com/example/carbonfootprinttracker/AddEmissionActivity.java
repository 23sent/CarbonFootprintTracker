package com.example.carbonfootprinttracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AddEmissionActivity extends AppCompatActivity {
    Button addTransportationBtn;
    Button addEnergyBtn;
    Button addAgricultureBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emission);

        // Set custom action bar.
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        View actionBar = getSupportActionBar().getCustomView();
        TextView pageHead = actionBar.findViewById(R.id.pageNameTxt);
        pageHead.setText("Add Emission");
        ImageView backBtn = actionBar.findViewById(R.id.backBtn);
        backBtn.setOnClickListener((View view) -> {
            onBackPressed();
        });


        ListView listview = (ListView) findViewById(R.id.categoryListView);
        CategoryAdapter adapter = new CategoryAdapter(this, EmissionTypes.Category.values());
        listview.setAdapter(adapter);
    }

    private void startAddActivity(EmissionTypes.Category category) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("category", category);
        Intent intent = new Intent(this, AddTransportationEmissionActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public class CategoryAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private EmissionTypes.Category[] categories;

        public CategoryAdapter(Activity activity, EmissionTypes.Category[] categories) {
            this.mInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.categories = categories;
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
            Button rowView;
            rowView = (Button) mInflater.inflate(R.layout.listview_button, null);
            rowView.setText(categories[i].fullName);
            rowView.setOnClickListener((View mView) -> {
                startAddActivity(categories[i]);
            });
            return rowView;
        }
    }
}