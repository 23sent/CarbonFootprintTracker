package com.example.carbonfootprinttracker;

import android.provider.BaseColumns;

public final class EmissionDBHelper {
    private EmissionDBHelper() {}

    public static class EmissionEntry implements BaseColumns {
        public static final String TABLE_NAME = "emission";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_CREATED_AT = "created_at";
        // Emission_Types = {Transportation, Energy, Agriculture}
        public static final String COLUMN_NAME_TYPE = "type";
        // Emission_Product_Types = {Car, Plane, Electricity, LPG, Bottled Water, Bread}
        public static final String COLUMN_NAME_PRODUCT_TYPE = "product_type";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        public static final String COLUMN_NAME_TOTAL = "total";
    }
}
