package com.example.carbonfootprinttracker;

public class CFTThread implements CFTRunnable {
    CarbonFootprintTracker app;
    float dailyEmission;
    float monthlyEmission;

    @Override
    public void setParams(CarbonFootprintTracker app) {
        this.app = app;
    }

    public void run() {
        this.dailyEmission = app.getDailyEmission();
        this.monthlyEmission = app.getMonthlyEmission();
    }

    public float getDailyEmission() {
        return dailyEmission;
    }

    public float getMonthlyEmission() {
        return monthlyEmission;
    }
}
