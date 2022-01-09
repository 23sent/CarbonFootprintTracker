package com.example.carbonfootprinttracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        CFTThread cftThread = new CFTThread();
        cftThread.setParams(app);
        Thread getEmissionsThread = new Thread(cftThread);
        getEmissionsThread.start();
        try {
            getEmissionsThread.join();
        } catch (InterruptedException e) {
            Log.e("CFTThread", "Error while joining cftThread: " + e.toString());
        }

        float dailyEmission = cftThread.getDailyEmission();
        float monthlyEmission = cftThread.getMonthlyEmission();
        float savedEmission = cftThread.getSavedEmission();
        float convertedSavedEmission = savedEmission;
        if (savedEmission != 0) {
            convertedSavedEmission = savedEmission * -1;
        }


        ft.add(R.id.dailyEmissionLayout, PieChartFragment.newInstance("Daily", dailyEmission+" kgCO2", "", new ArrayList<PieChart.PieSlice>()));
        ft.add(R.id.monthlyEmissionLayout, PieChartFragment.newInstance("Monthly", monthlyEmission+" kgCO2", "", new ArrayList<PieChart.PieSlice>()));
        ft.add(R.id.emissionSavedLayout, PieChartFragment.newInstance("Saved", convertedSavedEmission+" kgCO2", "", new ArrayList<PieChart.PieSlice>()));
        ft.commit();
    }
}