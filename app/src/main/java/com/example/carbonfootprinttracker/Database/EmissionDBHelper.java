package com.example.carbonfootprinttracker.Database;

import android.provider.BaseColumns;

/**
 * Hüseyin Emre Arı
 */
public final class EmissionDBHelper {
    private EmissionDBHelper() {}

    public static class EmissionEntry implements BaseColumns {
        public static final String TABLE_NAME = "emission";
        public static final String COLUMN_NAME_ID = "id";
        public static final String COLUMN_NAME_CREATED_AT = "created_at";
        // Emission_Category = {Transportation, Energy, Agriculture}
        public static final String COLUMN_NAME_CATEGORY = "category";
        // Emission_Product_Types = {Car, Plane, Electricity, LPG, Bottled Water, Bread}
        public static final String COLUMN_NAME_PRODUCT_TYPE = "type";
        public static final String COLUMN_NAME_QUANTITY = "quantity";
        // calculated emission amount
        public static final String COLUMN_NAME_TOTAL = "total";
    }
}
