package com.example.carbonfootprinttracker.Models;

import android.util.Log;

import com.example.carbonfootprinttracker.CFTApp;
import com.example.carbonfootprinttracker.Database.EmissionDBHandler;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

/**
 * A model to access and manage all emission.
 *
 * Hüseyin Emre Arı
 */
public class CarbonFootprintTracker {
    private ArrayList<Emission> emissions;
    EmissionDBHandler dbHandler;

    /**
     * Singleton design
     * Utku Sağocak
     */
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


    public void addEmissions(Emission e) {
        float quantity = e.getQuantity();
        String strQuantity= quantity + "";
        Log.d("infoQuantity", strQuantity);

        dbHandler.insertEmission(e);
        this.emissions.add(e);
    }


    public static boolean isEmissionToday(String emissionDate) {
        if (emissionDate == null) {
            return false;
        }
        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate emissionCreatedDate = LocalDate.parse(emissionDate, dtf);

        Date todaysDate = new Date();
        LocalDate localDate = todaysDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate nextDay = localDate.plusDays(1);
        LocalDate previousDay = localDate.minusDays(1);

        return emissionCreatedDate.isBefore(nextDay) && emissionCreatedDate.isAfter(previousDay);
    }

    public static boolean isEmissionThisMonth(String emissionDate) {
        if (emissionDate == null) {
            return false;
        }

        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate emissionCreatedDate = LocalDate.parse(emissionDate, dtf);
        Date todaysDate = new Date();
        LocalDate localDate = todaysDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate nextMonth = localDate.plusMonths(1);
        LocalDate previousMonth = localDate.minusMonths(1);

        return emissionCreatedDate.isBefore(nextMonth) && emissionCreatedDate.isAfter(previousMonth);
    }

    public static boolean isEmissionPreviousMonth(String emissionDate) {
        if (emissionDate == null) {
            return false;
        }

        final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        final LocalDate emissionCreatedDate = LocalDate.parse(emissionDate, dtf);
        Date todaysDate = new Date();
        LocalDate thisMonth = todaysDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
//        LocalDate thisMonth = localDate.minusMonths(1);
        LocalDate previous2Month = thisMonth.minusMonths(2);

        return emissionCreatedDate.isBefore(thisMonth) && emissionCreatedDate.isAfter(previous2Month);
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
        for(Emission emission : emissions) {
            if (emission.getDate() == null) {
                continue;
            }

            if (isEmissionThisMonth(emission.getDateString())) {
                total += emission.getCarbonFootprint();
            }
        }
        return total;
    }

    public float getPreviousMonthlyEmission() {
        float total = 0;
        for(Emission emission: emissions) {
            if (emission.getDate() == null) {
                continue;
            }

            if (isEmissionPreviousMonth(emission.getDateString())) {
                total += emission.getCarbonFootprint();
            }
        }

        return total;
    }

    public float getSavedEmission() {
        float thisMonthEmission = getMonthlyEmission();
        float previousMonthEmission = getPreviousMonthlyEmission();

        return savedEmission(thisMonthEmission, previousMonthEmission);
    }

    public void deleteEmission(Emission emission) {
        dbHandler.deleteEmission(emission.getId());
        this.emissions.removeIf(e -> e.getId() == emission.getId());
    }

    public float savedEmission(float thisMonthEmission, float previousMonthEmission) {
        // return thisMonthEmission - previousMonthEmission;
        if (previousMonthEmission != 0) {
            float savedEmission = 100f * thisMonthEmission / previousMonthEmission;
            if (savedEmission > 100) {
                return (savedEmission % 100) * -1;
            }
        }
        return 0;
    }

    public ArrayList<Emission> getEmissions() {
        return this.emissions;
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
