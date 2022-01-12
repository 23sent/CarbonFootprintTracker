package com.example.carbonfootprinttracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
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
import java.util.Objects;

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

        Objects.requireNonNull(getSupportActionBar()).hide();
        updateFragments(0, 0, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();

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
        updateFragments(dailyEmission, monthlyEmission, savedEmission);
    }

    public void updateFragments(float dailyEmission, float monthlyEmission, float savedEmission) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

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

        ft.replace(R.id.dailyEmissionLayout,
                PieChartFragment.newInstance("Daily",
                        String.format("%.2f ", dailyEmission) + EmissionTypes.Unit.KGCO2EQ.name,
                        String.format("%.1f", dailyRemain100) + "%", dailySlices),
                "dailyEmission");

        float monthlyRemain360 = 360 * monthlyEmission / idealEmissionPerMonth;
        float monthlyRemain100 = 100 * monthlyEmission / idealEmissionPerMonth;
        int monthlySliceColor = Color.argb(150, 0, 250, 0);
        if (monthlyRemain100 >= 100) {
            monthlySliceColor = Color.argb(150, 250, 0, 0);
        }
        ;

        monthlySlices.add(new PieChart.PieSlice(monthlySliceColor, monthlyRemain360));
        ft.replace(R.id.monthlyEmissionLayout,
                PieChartFragment.newInstance("Monthly",
                        String.format("%.2f ", monthlyEmission) + EmissionTypes.Unit.KGCO2EQ.name,
                        String.format("%.1f", monthlyRemain100) + "%", monthlySlices),
                "monthlyEmission");

        float savedEmission360 = 360 * savedEmission / 100;
        int savedSliceColor = Color.argb(150, 0, 250, 0);
        if (savedEmission < 0) {
            savedSliceColor = Color.argb(150, 250, 0, 0);
        }
        savedSlices.add(new PieChart.PieSlice(savedSliceColor, savedEmission360));

        ft.replace(R.id.emissionSavedLayout,
                PieChartFragment.newInstance("Saved",
                        String.format("%.2f", savedEmission) + "%",
                        String.format("%.2f", savedEmission) + "%",
                        savedSlices),
                "savedEmission");
        ft.commit();
    }
}