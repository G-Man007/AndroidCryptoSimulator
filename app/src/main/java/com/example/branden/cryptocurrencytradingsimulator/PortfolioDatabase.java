package com.example.branden.cryptocurrencytradingsimulator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Vector;

public class PortfolioDatabase extends DatabaseHelper {

    public static final String PTCol1 = "quantity";
    public static final String PTCol2 = "buyPrice";
    public static final String PTCol3 = "buyTime";
    public static final String PTCol4 = "buyDate";
    public static final String PTCol5 = "name";


    public Cursor mData;

    public static final String TradeHistory_Table = "tradeHistory";


    public PortfolioDatabase(Context context) {
        super(context, "portfolioTable");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + MDatabasename + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PTCol1 + " INTEGER " +
                PTCol2 + " REAL " + // 8-bit floating point number
                PTCol3 + " TEXT " +
                PTCol4 + " TEXT " +
                PTCol5 + " TEXT)";
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

    /**A getter that returns the date and time of when a crypto was bought.
     *
     * @return results contains an integer representing the number of crypto currency
     * @ccs.Pre-condition None
     * @ccs.Post-condition None
     **/
    public Vector<Integer> getQuantity() {
        Vector<Integer> results = new Vector();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " + PTCol1 + " From " + MDatabasename;
        Cursor data = db.rawQuery(query, null);

        int index = 0;
        if (data.moveToFirst()) {
            do {
                results.add(data.getInt(index));
                index++;
            } while (data.moveToNext());
        }

        return results;
    }

    /**getBuyPrice() is a getter that returns the price at which the crypto was bought
     *
     * @return results contains the price
     * @ccs.Pre-condition None
     * @ccs.Post-condition None
     **/
    public Vector<Integer> getBuyPrice() {
        Vector<Integer> results = new Vector();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " + PTCol2 + " From " + MDatabasename;
        Cursor data = db.rawQuery(query, null);

        int index = 0;
        if (data.moveToFirst()) {
            do {
                results.add(data.getInt(index));
                index++;
            } while (data.moveToNext());
        }

        return results;
    }

    /**A getter that returns the time that the user bought the crypto currency
     *
     * @return results contains the date
     * @ccs.Pre-condition None
     * @ccs.Post-condition None
     **/
    public Vector<String> getBuyTimes() {
        Vector<String> results = new Vector();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " + PTCol3 + " From " + MDatabasename;
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

    /**getDate() is a getter that returns the date and time of when a crypto was bought.
     *
     * @return results contains date
     * @ccs.Pre-condition None
     * @ccs.Post-condition None
     **/
    public Vector<String> getBuyDates() {
        Vector<String> results = new Vector();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " + PTCol4 + " From " + MDatabasename;
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

    /**A getter that returns the date and time of when a crypto was bought.
     *
     * @return results contains name of crypto currency
     * @ccs.Pre-condition None
     * @ccs.Post-condition None
     **/
    public Vector<String> getName() {
        Vector<String> results = new Vector();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select " + PTCol5 + " From " + MDatabasename;
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

    public void removeRow(int rowNum) {

    }
}
