package com.example.carbonfootprinttracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AddEmissionActivity extends AppCompatActivity {
    Button addTransportationBtn;
    Button addEnergyBtn;
    Button addAgricultureBtn;

    EmissionDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_emission);

        addTransportationBtn = (Button) findViewById(R.id.addTransportationBtn);
        addTransportationBtn.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, AddTransportationEmissionActivity.class);
            startActivity(intent);
        });

        addEnergyBtn = (Button) findViewById(R.id.addEnergyBtn);
        addEnergyBtn.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, AddEnergyEmissionActivity.class);
            startActivity(intent);
        });
    }
}