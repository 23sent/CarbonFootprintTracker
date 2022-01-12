package com.example.carbonfootprinttracker;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.carbonfootprinttracker.Models.CarbonFootprintTracker;

/**
 * Hüseyin Emre Arı
 */
public class CarbonFootPrinterUnitTest {
    @Test
    public void isEmissionToday_isCorrect() {
        assertEquals(CarbonFootprintTracker.isEmissionToday("2022-01-10"), false);
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

}
