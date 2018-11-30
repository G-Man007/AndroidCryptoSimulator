package com.example.branden.cryptocurrencytradingsimulator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import java.util.Vector;

public class PortfolioHistoryDatabase extends DatabaseHelper {

    public static final String PTCol1 = "portfolioValue";//abbreviations
    public static final String PTCol2 = "date";//mmddyy

    public Cursor mData;

    public PortfolioHistoryDatabase(Context context) {
        super(context, "PortfolioHistoryDatabase");
    }

    /**
     * onCreate is the default function called when starting an activity hence "onCreate" and runs the default
     * functions required based on the activity.
     *
     * @ccs.Pre-condition
     * @ccs.Post-condition
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + MDatabasename + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PTCol1 + " REAL " +
                PTCol2 + " TEXT)";//MMDDYY
        sqLiteDatabase.execSQL(createTable);

        SQLiteDatabase db = this.getWritableDatabase();
        //String query = "Select * From " + MDatabasename;
        //String query = "select * from " + MDatabasename + " where ID = ?";
        String query = "SELECT * FROM " + MDatabasename  + " WHERE ID = ?";
        Cursor data = db.rawQuery(query, null);
        mData =data;
    }

    public Cursor getData() {
        return mData;
    }

    /**A getter that returns the date and time of when a crypto currency was bought.
     *
     * @return results contains date
     * @ccs.Pre-condition None
     * @ccs.Post-condition None
     **/
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

    /** A getter that returns the date and time of when a crypto was bought.
     *
     * @return results contains date
     * @ccs.Pre-condition None
     * @ccs.Post-condition None
     **/
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
