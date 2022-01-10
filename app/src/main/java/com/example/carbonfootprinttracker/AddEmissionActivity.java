package com.example.carbonfootprinttracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AddEmissionActivity extends AppCompatActivity {
    Button addTransportationBtn;
    Button addEnergyBtn;
    Button addAgricultureBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emission);

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

        addTransportationBtn = (Button) findViewById(R.id.addTransportationBtn);
        addTransportationBtn.setOnClickListener((View v) -> {
            startAddActivity(EmissionTypes.Category.TRANSPORT);
        });

        addEnergyBtn = (Button) findViewById(R.id.addEnergyBtn);
        addEnergyBtn.setOnClickListener((View v) -> {
            startAddActivity(EmissionTypes.Category.ENERGY);
        });

        addAgricultureBtn = (Button) findViewById(R.id.addAgricultureBtn);
        addAgricultureBtn.setOnClickListener((View v) -> {
            startAddActivity(EmissionTypes.Category.AGRICULTURE);
        });
    }

    private void startAddActivity(EmissionTypes.Category category) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("category", category);
        Intent intent = new Intent(this, AddTransportationEmissionActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}