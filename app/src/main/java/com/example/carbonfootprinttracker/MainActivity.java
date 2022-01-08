package com.example.carbonfootprinttracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    Button addEmissionBtn;
    Button viewEmissionsBtn;
    CarbonFootprintTracker app = CarbonFootprintTracker.getInstance();
    TextView dailyEmissionsTxt;
    TextView monthlyEmissionTxt;

    EmissionDBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new EmissionDBHandler(this);

        addEmissionBtn = (Button) findViewById(R.id.addEmissionBtn);
        addEmissionBtn.setOnClickListener((View v) -> {
            Log.d(TAG, "onClick: addEmissionBtn");
            Intent intent = new Intent(this, AddEmissionActivity.class);
            startActivity(intent);
        });

        viewEmissionsBtn = (Button) findViewById(R.id.viewEmissionsBtn);
        viewEmissionsBtn.setOnClickListener((View v) -> {
            Log.d(TAG, app.toString());
        });

        dailyEmissionsTxt = (TextView) findViewById(R.id.dailyEmissionTxt);
        monthlyEmissionTxt = (TextView) findViewById(R.id.monthlyEmissionTxt);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ArrayList<Emission> emissions = dbHandler.getAllEmissions();
        dailyEmissionsTxt.setText(String.valueOf(app.getDailyEmission(emissions)));
        monthlyEmissionTxt.setText(String.valueOf(app.getMonthlyEmission()));
    }
}