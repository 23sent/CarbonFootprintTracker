package com.example.carbonfootprinttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

        getSupportActionBar().hide();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
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

        float idealEmissionPerYear = 2000;
        float idealEmissionPerMonth = 166.6f;
        float idealEmissionPerDay = 5.5f;

        ArrayList<PieChart.PieSlice> dailySlices = new ArrayList<>();
        ArrayList<PieChart.PieSlice> monthlySlices = new ArrayList<>();
        ArrayList<PieChart.PieSlice> savedSlices = new ArrayList<>();

        float dailyRemain360 = 360 * dailyEmission / idealEmissionPerDay;
        float dailyRemain100 = 100 * dailyEmission / idealEmissionPerDay;
        int dailySliceColor = Color.argb(150, 0, 250, 0);
        if (dailyRemain100 >= 100) {
            dailySliceColor = Color.argb(150, 250, 0, 0);
        }

        dailySlices.add(new PieChart.PieSlice(dailySliceColor, dailyRemain360));

        ft.add(R.id.dailyEmissionLayout, PieChartFragment.newInstance("Daily", dailyEmission + " kgCO2", String.format("%.1f", dailyRemain100)+"%", dailySlices));

        float monthlyRemain360 = 360 * monthlyEmission / idealEmissionPerMonth;
        float monthlyRemain100 = 100 * monthlyEmission / idealEmissionPerMonth;
        int monthlySliceColor = Color.argb(150, 0, 250, 0);
        if (monthlyRemain100 >= 100) {
            monthlySliceColor = Color.argb(150, 250, 0, 0);
        };

        monthlySlices.add(new PieChart.PieSlice(monthlySliceColor, monthlyRemain360));
        ft.add(R.id.monthlyEmissionLayout, PieChartFragment.newInstance("Monthly", monthlyEmission + " kgCO2", String.format("%.1f", monthlyRemain100)+"%", monthlySlices));

        ft.add(R.id.emissionSavedLayout, PieChartFragment.newInstance("Saved", convertedSavedEmission + " kgCO2", "", savedSlices));
        ft.commit();
    }


}