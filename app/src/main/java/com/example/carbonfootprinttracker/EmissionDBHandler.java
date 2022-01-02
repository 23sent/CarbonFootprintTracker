package com.example.carbonfootprinttracker;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EmissionDBHandler extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "emission.db";

    private final String SQL_CREATE_EMISSION_TABLE = "CREATE TABLE IF NOT EXISTS " +
            EmissionDBHelper.EmissionEntry.TABLE_NAME + "(" +
            EmissionDBHelper.EmissionEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            EmissionDBHelper.EmissionEntry.COLUMN_NAME_CREATED_AT + " DATETIME NOT NULL, " +
            EmissionDBHelper.EmissionEntry.COLUMN_NAME_TYPE + " INTEGER NOT NULL, " +
            EmissionDBHelper.EmissionEntry.COLUMN_NAME_PRODUCT_TYPE + " INTEGER NOT NULL, " +
            EmissionDBHelper.EmissionEntry.COLUMN_NAME_QUANTITY + " REAL NOT NULL," +
            EmissionDBHelper.EmissionEntry.COLUMN_NAME_TOTAL + " REAL NOT NULL"+  ")";

    private final String SQL_DELETE_EMISSION_TABLE = "DROP TABLE IF EXISTS " +
            EmissionDBHelper.EmissionEntry.TABLE_NAME;

    public EmissionDBHandler(Context context) {
        super (context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
         sqLiteDatabase.execSQL(SQL_CREATE_EMISSION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL(SQL_DELETE_EMISSION_TABLE);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        onUpgrade(sqLiteDatabase, oldVersion, newVersion);
    }
}
