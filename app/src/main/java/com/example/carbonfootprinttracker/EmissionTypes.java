package com.example.carbonfootprinttracker;

import java.util.ArrayList;
import java.util.List;


public class EmissionTypes {

    public enum Unit {
        KM("km", "Distance"),
        KG("kg", "Consumption (kg)"),
        HOURS("hours", "Duration (h)"),
        KWH("kWh", "Consumption (kWh)");

        String name;
        String tag;

        Unit(String n, String t) {
            this.name = n;
            this.tag = t;
        }
    }

    public enum Category {
        TRANSPORT("Transportation", R.drawable.ic_baseline_directions_car_75),
        ENERGY("Natural Res.", R.drawable.ic_baseline_electricity_75),
        AGRICULTURE("Agriculture & Food", R.drawable.ic_baseline_food_75);
        String name;
        int imageResource;

        Category(String name, int img) {
            this.name = name;
            this.imageResource = img;
        }
    }

    public enum Type {
        BUS(Category.TRANSPORT, "Bus", 0.000103f * 60, Unit.HOURS),
        PLANE(Category.TRANSPORT, "Plane", 0.00034f * 60, Unit.HOURS),
        CAR(Category.TRANSPORT, "Car", 0.000257f * 60, Unit.HOURS),

        RED_MEAT(Category.AGRICULTURE, "Red Meat", (39.2f + 27.0f) / 2f, Unit.KG),
        WHITE_MEAT(Category.AGRICULTURE, "White Meat", 6.9f, Unit.KG),
        FISH(Category.AGRICULTURE, "Fish", 6.1f, Unit.KG),

        ELECTRICITY(Category.ENERGY, "Electricity", 0.394f, Unit.KWH);

        Category category;
        String name;
        float factor;
        Unit unit;

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
