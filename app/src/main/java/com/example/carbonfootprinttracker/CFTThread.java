package com.example.carbonfootprinttracker;

public class CFTThread implements CFTRunnable {
    CarbonFootprintTracker app;
    private float dailyEmission;
    private float monthlyEmission;
    private float savedEmission;

    @Override
    public void setParams(CarbonFootprintTracker app) {
        this.app = app;
    }

    public void run() {
        this.dailyEmission = app.getDailyEmission();
        this.monthlyEmission = app.getMonthlyEmission();
        this.savedEmission = app.getSavedEmission();
    }

    public float getDailyEmission() {
        return dailyEmission;
    }

    public float getMonthlyEmission() {
        return monthlyEmission;
    }

    public float getSavedEmission() {
        return savedEmission;
    }
}
