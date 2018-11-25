package com.example.branden.cryptocurrencytradingsimulator;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class PortfolioHistoryDatabase extends DatabaseHelper {

  public static final String PTCol1 = "portfolioValue";//abbreviations
  public static final String PTCol2 = "data";//mmddyy

  public PortfolioHistoryDatabase(Context context) {
    super(context, "PortfolioHistoryDatabase");
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
    PTCol1 + "INTEGER" +
    PTCol2 + "TEXT";//MMDDYY
    sqLiteDatabase.execSQL(createTable);
  }
}
