package com.example.carbonfootprinttracker;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;


public class CarbonFootPrinterUnitTest {
    @Test
    public void isEmissionToday_isCorrect() {
        assertEquals(CarbonFootprintTracker.isEmissionToday("2022-01-12"), false);
        assertEquals(CarbonFootprintTracker.isEmissionToday("2022-01-11"), false);
    }

    @Test
    public void isEmissionThisMonth_isCorrect() {
        assertTrue(CarbonFootprintTracker.isEmissionThisMonth("2022-01-31"));
        assertTrue(CarbonFootprintTracker.isEmissionThisMonth("2022-02-01"));
    }

    @Test
    public void isEmissionPreviousMonth_isCorrect() {
        assertTrue(CarbonFootprintTracker.isEmissionPreviousMonth("2021-11-30"));
        assertFalse(CarbonFootprintTracker.isEmissionPreviousMonth("2021-02-15"));
    }

    @Test
    public void getDailyEmissions_isCorrect() {
        CarbonFootprintTracker cft = CarbonFootprintTracker.getInstance();

        Emission e1 = new Emission(1, 20, 20, new Date(), EmissionTypes.Type.CAR);
        Emission e2 = new Emission(2, 20, 20, new Date(), EmissionTypes.Type.BUS);

        cft.addEmissions(e1);
        cft.addEmissions(e2);

        ArrayList<Emission> emissions = cft.getEmissions();
        assertEquals(emissions.size(), 2);

    }
}
