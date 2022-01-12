package com.example.carbonfootprinttracker.Activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

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

import com.example.carbonfootprinttracker.Adapters.CategoryAdapter;
import com.example.carbonfootprinttracker.EmissionTypes;
import com.example.carbonfootprinttracker.R;

/**
 * Utku Sağocak
 */
public class AddEmissionActivity extends AppCompatActivity implements CategoryAdapter.CategoryAdapterListener {
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
        CategoryAdapter adapter = new CategoryAdapter(this, EmissionTypes.Category.values(), this);
        listview.setAdapter(adapter);
    }

    @Override
    public void onClickCategory(EmissionTypes.Category category) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("category", category);
        Intent intent = new Intent(this, AddEmissionFromCategoryActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}