package com.example.carbonfootprinttracker.Database;

import static com.example.carbonfootprinttracker.Database.EmissionDBHelper.EmissionEntry.COLUMN_NAME_CATEGORY;
import static com.example.carbonfootprinttracker.Database.EmissionDBHelper.EmissionEntry.COLUMN_NAME_CREATED_AT;
import static com.example.carbonfootprinttracker.Database.EmissionDBHelper.EmissionEntry.COLUMN_NAME_ID;
import static com.example.carbonfootprinttracker.Database.EmissionDBHelper.EmissionEntry.COLUMN_NAME_PRODUCT_TYPE;
import static com.example.carbonfootprinttracker.Database.EmissionDBHelper.EmissionEntry.COLUMN_NAME_QUANTITY;
import static com.example.carbonfootprinttracker.Database.EmissionDBHelper.EmissionEntry.COLUMN_NAME_TOTAL;
import static com.example.carbonfootprinttracker.Database.EmissionDBHelper.EmissionEntry.TABLE_NAME;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.carbonfootprinttracker.EmissionTypes;
import com.example.carbonfootprinttracker.Models.Emission;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

/**
 * Hüseyin Emre Arı
 */
public class EmissionDBHandler extends SQLiteOpenHelper {
    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "emission.db";

    private final String SQL_CREATE_EMISSION_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + "(" +
            EmissionDBHelper.EmissionEntry.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            EmissionDBHelper.EmissionEntry.COLUMN_NAME_CREATED_AT + " TEXT NOT NULL, " +
            EmissionDBHelper.EmissionEntry.COLUMN_NAME_CATEGORY + " TEXT NOT NULL, " +
            EmissionDBHelper.EmissionEntry.COLUMN_NAME_PRODUCT_TYPE + " TEXT NOT NULL, " +
            EmissionDBHelper.EmissionEntry.COLUMN_NAME_QUANTITY + " REAL NOT NULL," +
            EmissionDBHelper.EmissionEntry.COLUMN_NAME_TOTAL + " REAL NOT NULL"+  ")";

    private final String SQL_DELETE_EMISSION_TABLE = "DROP TABLE IF EXISTS " +
            TABLE_NAME;

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

    public ArrayList<Emission> getAllEmissions() {
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                COLUMN_NAME_ID,
                COLUMN_NAME_CREATED_AT,
                COLUMN_NAME_CATEGORY,
                COLUMN_NAME_QUANTITY,
                COLUMN_NAME_PRODUCT_TYPE,
                COLUMN_NAME_TOTAL,
        };

        String orderBy = COLUMN_NAME_CREATED_AT + " DESC";

        Cursor cursor = db.query(
                TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                orderBy
        );

        ArrayList emissions = new ArrayList<Emission>();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_NAME_ID));
            float quantity = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_NAME_QUANTITY));
            String createdAt = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_CREATED_AT));
            String type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME_PRODUCT_TYPE));
            float total = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_NAME_TOTAL));

            EmissionTypes.Type emissionType = EmissionTypes.Type.valueOf(type);
            Date dateCreatedAt = convertStringToDate(createdAt);
            if (dateCreatedAt == null) {
                continue;
            }
            Emission e = new Emission(id, quantity, total, dateCreatedAt, emissionType);
            emissions.add(e);
        }
        return emissions;
    }

    public Emission insertEmission(Emission e) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_NAME_QUANTITY, e.getQuantity());
        values.put(COLUMN_NAME_CREATED_AT, String.valueOf(e.getDateString()));
        values.put(COLUMN_NAME_CATEGORY, String.valueOf(e.getType().category));
        values.put(COLUMN_NAME_PRODUCT_TYPE, String.valueOf(e.getType()));
        values.put(COLUMN_NAME_TOTAL, e.getCarbonFootprint());

        long newRowId = db.insert(TABLE_NAME, null, values);
        e.setId(newRowId);
        Log.d("DB Insert", "New Emission Inserted to the DB with ID: " + newRowId);
        return e;
    }

    public void deleteEmission(long emissionId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String where = COLUMN_NAME_ID + " = ?";
        String[] whereArgs = {String.valueOf(emissionId)};
        int deletedRow = db.delete(TABLE_NAME, where, whereArgs);
        Log.d("Emission Deleted", "deleted emission row: " + deletedRow);
    }

    private Date convertStringToDate(String strDate) {
        Date date;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        try {
            date = formatter.parse(strDate);
        } catch (Exception e) {
            Log.e("Convert String To Date", "Error while converting string to date: " + e.toString());
            return null;
        }

        return date;
    }
}
