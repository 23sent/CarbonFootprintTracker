package com.example.carbonfootprinttracker;

import android.util.Log;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class CarbonFootprintTracker {
    private ArrayList<Emission> emissions;
    EmissionDBHandler dbHandler;

    private static CarbonFootprintTracker instance;
    private CarbonFootprintTracker() {
        emissions = new ArrayList<>();
        dbHandler = new EmissionDBHandler(CFTApp.getContext());
        emissions = dbHandler.getAllEmissions();
    }

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
        float quantity = e.getQuantity();
        String strQuantity= quantity + "";
        Log.d("infoQuantity", strQuantity);

        dbHandler.insertEmission(e);
        this.emissions.add(e);
    }

    public void updateEmissions(Emission e) {

    }

    public boolean isEmissionToday(String emissionDate) {
        if (emissionDate == null) {
            return false;
        }
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate emissionCreatedDate = LocalDate.parse(emissionDate, dateTimeFormatter);
        Date todaysDate = new Date();
        LocalDate localDate = todaysDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate nextDay = localDate.plusDays(1);
        LocalDate previousDay = localDate.minusDays(1);

        return emissionCreatedDate.isBefore(nextDay) && emissionCreatedDate.isAfter(previousDay);
    }

    public float getDailyEmission() {
        float totalEmission = 0;
        for (Emission emission : emissions) {
            if (emission.getDate() == null) {
                continue;
            }
            if(isEmissionToday(emission.getDateString())) {
                totalEmission += emission.getCarbonFootprint();
            }
        }
        return totalEmission;
    }

    public float getMonthlyEmission() {
        float total = 0;
        for(Emission e : emissions) {
            total = total + e.getQuantity();
        }
        return total;
    }

    public ArrayList<Emission> getEmissions() {
        return emissions;
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
