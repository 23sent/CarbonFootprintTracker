package com.example.carbonfootprinttracker;

import java.util.Date;

public class Emission {
    private float quantity = 0;
    private float carbonFootprint = 0;
    private Date date;
    private EmissionTypes.Type type = null;

    public Emission() {
        date = new Date();
        calculateCarbonFootprint();
    }


    public void setQuantity(float quantity) {
        this.quantity = quantity;
        calculateCarbonFootprint();
    }

    public float getQuantity() {
        return quantity;
    }

    public void setType(EmissionTypes.Type type) {
        this.type = type;
        calculateCarbonFootprint();
    }

    public EmissionTypes.Type getType() {
        return type;
    }


    public void setDate(Date d) {
        this.date = d;
    }

    public Date getDate() {
        return date;
    }

    public float getCarbonFootprint() {
        return carbonFootprint;
    }

    public void calculateCarbonFootprint() {
        if (this.type != null && this.quantity > 0) {
            this.carbonFootprint = this.type.factor * this.quantity;
        } else {
            this.carbonFootprint = 0;
        }
    }


    @Override
    public String toString() {
        return "Emission: " + "quantity=" + quantity + ';';
    }
}
