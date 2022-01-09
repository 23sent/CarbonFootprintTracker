package com.example.carbonfootprinttracker;

import android.widget.Toast;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Emission implements Serializable {
    private float quantity = 0;
    private float carbonFootprint = 0;
    private Date date;
    private EmissionTypes.Type type = null;
    private long id;

    public Emission() {
        setDate(Calendar.getInstance().getTime());
        calculateCarbonFootprint();
    }

    public Emission(long id, float quantity, float carbonFootprint, Date date, EmissionTypes.Type type) {
        this.id = id;
        this.quantity = quantity;
        this.carbonFootprint = carbonFootprint;
        this.date = date;
        this.type = type;
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
        Toast.makeText(CFTApp.getContext(), getDateString(), Toast.LENGTH_SHORT).show();
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

    public long getId() {
        return id;
    }

    public String getDateString() {
        return getDateString("yyyy-MM-dd");
    }

    public String getDateString(String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        if (this.date != null) {
            return sdf.format(this.date);
        }
        return "-";
    }

    public String getCarbonFootprintString() {
        return this.carbonFootprint+" kgCO2eq";
    }

    public String getTypeString() {
        if (this.type != null) {
            return type.category.name +" - "+type.name;
        }
        return "-";
    }

    @Override
    public String toString() {
        return "Emission: " + "quantity=" + quantity + ';';
    }
}
