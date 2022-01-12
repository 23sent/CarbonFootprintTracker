package com.example.carbonfootprinttracker;

import java.util.ArrayList;
import java.util.List;

/**
 * Emission Types, Categories and Units
 * https://www.winnipeg.ca/finance/findata/matmgt/documents/2012/682-2012/682-2012_Appendix_H-WSTP_South_End_Plant_Process_Selection_Report/Appendix%207.pdf
 *
 * Utku Sağocak
 */
public class EmissionTypes {

    public enum Unit {
        KM("km", "Distance (km)"),
        KG("kg", "Consumption (kg)"),
        HOURS("hours", "Duration (h)"),
        KWH("kWh", "Consumption (kWh)"),
        M3("m3", "Consumption (cubic meter)"),
        KGCO2EQ("kgCo2-Eq", "Kg CO2-Equivalent");

        public final String name;
        public final String tag;

        Unit(String n, String t) {
            this.name = n;
            this.tag = t;
        }
    }

    public enum Category {
        TRANSPORT("Transportation", R.drawable.ic_baseline_directions_car_75),
        ENERGY("Natural Res.", "Energy & Natural Resources", R.drawable.ic_baseline_electricity_75),
        AGRICULTURE("Agriculture & Food", R.drawable.ic_baseline_food_75),
        CUSTOM("Custom Emission", R.drawable.ic_baseline_build_circle_70);
        public final String name;
        public final String fullName;
        public final int imageResource;

        Category(String name, String fullName, int img) {
            this.name = name;
            this.fullName = fullName;
            this.imageResource = img;
        }

        Category(String name, int img) {
            this.name = name;
            this.fullName = name;
            this.imageResource = img;
        }
    }

    public enum Type {
        BUS(Category.TRANSPORT, "Bus", 0.000103f * 60, Unit.HOURS),
        PLANE(Category.TRANSPORT, "Plane", 0.00034f * 60, Unit.HOURS),
        CAR(Category.TRANSPORT, "Car", 0.19f, Unit.KM),

        RED_MEAT(Category.AGRICULTURE, "Red Meat", (39.2f + 27.0f) / 2f, Unit.KG),
        WHITE_MEAT(Category.AGRICULTURE, "White Meat", 6.9f, Unit.KG),
        FISH(Category.AGRICULTURE, "Fish", 6.1f, Unit.KG),

        ELECTRICITY(Category.ENERGY, "Electricity", 0.394f, Unit.KWH),
        WATER(Category.ENERGY, "Water", 0.32f, Unit.M3),
        NATURAL_GAS(Category.ENERGY, "Natural Gas", 0.21f, Unit.KWH),
        CUSTOM_EMISSION(Category.CUSTOM, "Emission", 1.0f, Unit.KGCO2EQ);

        public final Category category;
        public final String name;
        public final float factor;
        public final Unit unit;

        Type(Category c, String n, float f, Unit u) {
            this.category = c;
            this.name = n;
            this.factor = f;
            this.unit = u;
        }
    }

    public static List<Type> getTypes(Category category) {
        Type[] values = Type.values();
        List<Type> result = new ArrayList<>();
        for (Type t : values) {
            if (t.category == category) {
                result.add(t);
            }
        }

        return result;
    }

    public static String[] getNames(List<Type> values) {
        String[] names = new String[values.size()];

        int i = 0;
        for (Type t : values) {
            names[i] = t.name;
            i++;
        }
        return names;
    }
}
