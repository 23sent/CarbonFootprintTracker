package com.example.carbonfootprinttracker;

import java.util.ArrayList;

public class CarbonFootprintTracker {
    private ArrayList<Emission> emissions = new ArrayList<>();

    private static CarbonFootprintTracker instance;
    private CarbonFootprintTracker() {}
    public static CarbonFootprintTracker getInstance() {
        if (instance == null) {
            instance = new CarbonFootprintTracker();
        }
        return instance;
    }

    public void setEmissions(ArrayList<Emission> emissions) {
        this.emissions = emissions;
    }

    public void addEmissions(Emission e) {
        this.emissions.add(e);
    }

    public void updateEmissions(Emission e) {

    }

    public float getDailyEmission() {
        float total = 0;
        for(Emission e : emissions) {
            total = total + e.getQuantity();
        }
        return total;
    }

    public float getMonthlyEmission() {
        float total = 0;
        for(Emission e : emissions) {
            total = total + e.getQuantity();
        }
        return total;
    }

    @Override
    public String toString() {
        String res = "";
        for(Emission e : emissions) {
            res = res + e.toString();
        }
        return res;
    }
}
