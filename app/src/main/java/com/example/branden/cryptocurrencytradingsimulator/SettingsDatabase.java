package com.example.branden.cryptocurrencytradingsimulator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.Vector;

public class SettingsDatabase extends DatabaseHelper {

    public static final String PTCol1 = "buyingPower";

    public SettingsDatabase(Context context) {
        super(context, "settingsDatabase");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + MDatabasename + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PTCol1 + "REAL";
        sqLiteDatabase.execSQL(createTable);
    }

    public Vector<Double> getBuyingPower() {
        Vector<Double> results = new Vector();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " + PTCol1 + " From " + MDatabasename;
        Cursor data = db.rawQuery(query, null);

        int index = 0;
        if (data.moveToFirst()) {
            do {
                results.add(data.getDouble(index));
                index++;
            } while (data.moveToNext());
        }

        return results;
    }
}

