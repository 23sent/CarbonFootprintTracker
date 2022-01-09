package com.example.carbonfootprinttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.carbonfootprinttracker.Graphs.PieChart;
import com.example.carbonfootprinttracker.Graphs.PieChartFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    Button addEmissionBtn;
    Button viewEmissionsBtn;
    CarbonFootprintTracker app;
    TextView dailyEmissionsTxt;
    TextView monthlyEmissionTxt;


    PieChart dailyPieChart;
    PieChart monthlyPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        app = CarbonFootprintTracker.getInstance();

        addEmissionBtn = (Button) findViewById(R.id.addEmissionBtn);
        addEmissionBtn.setOnClickListener((View v) -> {
            Log.d(TAG, "onClick: addEmissionBtn");
            Intent intent = new Intent(this, AddEmissionActivity.class);
            startActivity(intent);
        });

        viewEmissionsBtn = (Button) findViewById(R.id.viewEmissionsBtn);
        viewEmissionsBtn.setOnClickListener((View v) -> {
            Log.d(TAG, app.toString());
            Log.d(TAG, "onClick: viewEmissionBtn");
            Intent intent = new Intent(this, ViewEmissionsActivity.class);
            startActivity(intent);
        });

//        dailyEmissionsTxt = (TextView) findViewById(R.id.dailyEmissionTxt);
//        monthlyEmissionTxt = (TextView) findViewById(R.id.monthlyEmissionTxt);
//
//        dailyPieChart = (PieChart) findViewById(R.id.dailyPieChart);
//        dailyPieChart.setBackCircleColor(Color.argb(50, 0, 0, 0));
//        setDailyPieChart();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.dailyEmissionLayout, PieChartFragment.newInstance("Daily", "10kgCo2"));
        ft.add(R.id.monthlyEmissionLayout, PieChartFragment.newInstance("Monthly", "15kgCo2"));
        ft.add(R.id.emissionSavedLayout, PieChartFragment.newInstance("EmissionSaved", "15kgCo2"));

        ft.commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        ArrayList<Emission> emissions = dbHandler.getAllEmissions();
//        dailyEmissionsTxt.setText(String.valueOf(app.getDailyEmission()));
//        monthlyEmissionTxt.setText(String.valueOf(app.getMonthlyEmission()));
    }

    private void setDailyPieChart() {
        PieChart.PieSlice slice = new PieChart.PieSlice(Color.GRAY, 150);
        dailyPieChart.addPieSlice(slice);
    }
}