package com.example.branden.cryptocurrencytradingsimulator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import java.util.Vector;

public class PortfolioHistoryDatabase extends DatabaseHelper {

    public static final String PTCol1 = "portfolioValue";//abbreviations
    public static final String PTCol2 = "date";//mmddyy

    public PortfolioHistoryDatabase(Context context) {
        super(context, "PortfolioHistoryDatabase");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + MDatabasename + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PTCol1 + "REAL" +
                PTCol2 + "TEXT";//MMDDYY
        sqLiteDatabase.execSQL(createTable);
    }

    public Vector<String> getDate() {
        Vector<String> results = new Vector();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " + PTCol2 + " From " + MDatabasename;
        Cursor data = db.rawQuery(query, null);

        int index = 0;
        if (data.moveToFirst()) {
            do {
                results.add(data.getString(index));
                index++;
            } while (data.moveToNext());
        }

        return results;
    }

    public Vector<Double> getPortfolioValue() {
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
