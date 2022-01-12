package com.example.carbonfootprinttracker;
import org.junit.Test;
import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class EmissionUnitTest {
    @Test
    public void getDateString_isCorrect() {
        Emission emission = new Emission();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Calendar defCalendar = GregorianCalendar.getInstance(TimeZone.getDefault());
        String todayString = sdf.format(defCalendar.getTime());

        // Is created date today?
        assertEquals(sdf.format(emission.getDate()), todayString);
        // Test dateString method.
        assertEquals(emission.getDateString("dd-MM-yyyy"), todayString);
        assertNotEquals(emission.getDateString(), todayString);
    }

    @Test
    public void calculateCarbonFootprint_isCorrect() {
        Emission emission = new Emission(0, 10, 0, new Date(), EmissionTypes.Type.CAR);
        assertEquals(emission.getCarbonFootprint(), EmissionTypes.Type.CAR.factor * 10f, 0.0);
    }
}
