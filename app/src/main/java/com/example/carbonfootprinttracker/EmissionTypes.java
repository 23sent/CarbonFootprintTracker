package com.example.carbonfootprinttracker;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.function.Function;

public class EmissionTypes {
    public enum Category {
        TRANSPORT, ENERGY, AGRICULTURE
    }

    public enum Type {
        BUS(Category.TRANSPORT, "Bus", 0.000103f),
        PLANE(Category.TRANSPORT, "Plane", 0.00034f),
        CAR(Category.TRANSPORT, "Car", 0.000257f),

        RED_MEAT(Category.AGRICULTURE,"Red Meat", (39.2f + 27.0f) / 2f),
        WHITE_MEAT(Category.AGRICULTURE,"White Meat", 6.9f),
        FISH(Category.AGRICULTURE,"Fish", 6.1f);

        Category category;
        String name;
        float factor;

        Type(Category c, String n, float f) {
            this.category = c;
            this.name = n;
            this.factor = f;
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
