package com.example.carbonfootprinttracker;

import java.util.Date;

public class Emission {
    private int quantity = 0;
    private int cfFactor = 2;
    private float carbonFootprint = 0;
    private Date date;

    public Emission() {
        date = new Date();
        calculateCarbonFootprint();
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        calculateCarbonFootprint();
    }

    public void setDate(Date d) {
        this.date = d;
    };

    public Date getDate() {
        return date;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getCarbonFootprint() {
        return carbonFootprint;
    }

    public void calculateCarbonFootprint() {
        this.carbonFootprint = this.cfFactor * this.quantity;
    }

    @Override
    public String toString() {
        return "Emission: " + "quantity=" + quantity + ';';
    }
}
