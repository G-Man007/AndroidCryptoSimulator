package com.example.branden.cryptocurrencytradingsimulator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.Vector;

public class TransacationsDatabase extends DatabaseHelper{

  public static final String PTCol1 = "name";
  public static final String PTCol2 = "transacationsType";//0= sell 1 = buy
  public static final String PTCol3 = "quantity";
  public static final String PTCol4 = "price";

  public TransacationsDatabase(Context context) {
    super(context, "transacationsDatabase");
  }

  /**
  * onCreate is the default function called when starting an activity hence "onCreate" and runs the default
  * functions required based on the activity.
  *
  * @ccs.Pre-condition
  * @ccs.Post-condition
  * */
  @Override
  public void onCreate(SQLiteDatabase sqLiteDatabase) {
    String createTable = "CREATE TABLE " + MDatabasename + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
    PTCol1 + "TEXT" +
    PTCol2 + "INTEGER" + // 8-bit floating point number
    PTCol3 + "INTEGER" +
    PTCol4 + "REAL";
    sqLiteDatabase.execSQL(createTable);
  }

  /**
  *
  * @ccs.Pre-condition Function can be called at any time updated prices are wanted for all of the coins
  * @ccs.Post-condition Will initialize a base data structure to pull from
  *
  * @return results contains price data
  **/
  public Vector<String> getNames(){
    Vector<String> results = new Vector();
    SQLiteDatabase db = this.getWritableDatabase();
    String query = "Select * From " + MDatabasename;
    Cursor data = db.rawQuery(query, null);

    int index =0;
    if (data.moveToFirst()) {
      do {
        results.add(data.getString(index));
        index++;
      } while (data.moveToNext());
    }

    return results;
  }

  /**
  *
  * @ccs.Pre-condition Function can be called at any time updated prices are wanted for all of the coins
  * @ccs.Post-condition Will initialize a base data structure to pull from
  *
  * @return results contains price data
  **/
  public Vector<Integer> getTransacationsType(){
    Vector<Integer> results = new Vector();
    SQLiteDatabase db = this.getWritableDatabase();
    String query = "Select * From " + MDatabasename;
    Cursor data = db.rawQuery(query, null);

    int index =0;
    if (data.moveToFirst()) {
      do {
        results.add(data.getInt(index));
        index++;
      } while (data.moveToNext());
    }

    return results;
  }

  /**
  *
  * @ccs.Pre-condition Function can be called at any time updated prices are wanted for all of the coins
  * @ccs.Post-condition Will initialize a base data structure to pull from
  *
  * @return results contains price data
  **/
  public Vector<Integer> getQuantity(){
    Vector<Integer> results = new Vector();
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

    return results;
  }

  /**
  *
  * @ccs.Pre-condition Function can be called at any time updated prices are wanted for all of the coins
  * @ccs.Post-condition Will initialize a base data structure to pull from
  *
  * @return results contains price data
  **/
  public Vector<Double> getPrice(){
    Vector<Double> results = new Vector();
    SQLiteDatabase db = this.getWritableDatabase();
    String query = "Select * From " + MDatabasename;
    Cursor data = db.rawQuery(query, null);

    int index =0;
    if (data.moveToFirst()) {
      do {
        results.add(data.getDouble(index));
        index++;
      } while (data.moveToNext());
    }

    return results;
  }


}
