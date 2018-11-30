package com.example.branden.cryptocurrencytradingsimulator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Vector;

public class TransactionsDatabase extends DatabaseHelper {

    private static final String PTCol1 = "name";
    private static final String PTCol2 = "transacationsType";//0= sell 1 = buy
    private static final String PTCol3 = "quantity";
    private static final String PTCol4 = "price";

    public TransactionsDatabase(Context context) {
        super(context, "transacationsDatabase");
    }

    public Cursor mData;

    /**
     * The default function called when starting an activity hence "onCreate" and runs the default
     * functions required based on the activity.
     *
     * @ccs.Pre-condition
     * @ccs.Post-condition
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + MDatabasename + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PTCol1 + " TEXT " +
                PTCol2 + " INTEGER " + // 8-bit floating point number
                PTCol3 + " INTEGER " +
                PTCol4 + " REAL)";
        sqLiteDatabase.execSQL(createTable);

        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + MDatabasename  + " WHERE ID = ?";
        Cursor data = db.rawQuery(query, null);
        mData =data;
    }

    public Cursor getData() {
        return mData;
    }

    /** Returns the names of the crypto currency.
     *
     * @return results contains the names of the crypto currency
     * @ccs.Pre-condition None
     * @ccs.Post-condition None
     **/
    public Vector<String> getNames() {
        Vector<String> results = new Vector<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * From " + MDatabasename;
        Cursor data = db.rawQuery(query, null);

        int index = 0;
        if (data.moveToFirst()) {
            do {
                results.add(data.getString(index));
                index++;
            } while (data.moveToNext());
        }
        data.close();
        return results;
    }
    /** Returns whether the user is buying or selling the crypto currency.
     *
     * @return results contains either buy or sell
     * @ccs.Pre-condition None
     * @ccs.Post-condition None
     **/
    public Vector<Integer> getTransacationsType() {
        Vector<Integer> results = new Vector<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * From " + MDatabasename;
        Cursor data = db.rawQuery(query, null);

        int index = 0;
        if (data.moveToFirst()) {
            do {
                results.add(data.getInt(index));
                index++;
            } while (data.moveToNext());
        }
        data.close();
        return results;
    }
    /**getQuantity is a getter that returns an integer of the number of coins that the user has.
     *
     * @return results contains quantity
     * @ccs.Pre-condition None
     * @ccs.Post-condition None
     **/
    public Vector<Integer> getQuantity() {
        Vector<Integer> results = new Vector<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * From " + MDatabasename;
        Cursor data = db.rawQuery(query, null);

        int index = 0;
        if (data.moveToFirst()) {
            do {
                results.add(data.getInt(index));
                index++;
            } while (data.moveToNext());
        }
        data.close();
        return results;
    }
    /**getPrice is a getter that returns the price of the crypto.
     *
     * @return results contains price of the crypto
     * @ccs.Pre-condition None
     * @ccs.Post-condition None
     **/
    public Vector<Double> getPrice() {
        Vector<Double> results = new Vector<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * From " + MDatabasename;
        Cursor data = db.rawQuery(query, null);

        int index = 0;
        if (data.moveToFirst()) {
            do {
                results.add(data.getDouble(index));
                index++;
            } while (data.moveToNext());
        }
        data.close();
        return results;
    }
}
