package com.example.branden.cryptocurrencytradingsimulator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class PortfolioDatabase extends DatabaseHelper {

    public static final String PTCol1 = "quantity";
    public static final String PTCol2 = "buyPrice";
    public static final String PTCol3 = "buyTime";
    public static final String PTCol4 = "buyDate";


    public static final String TradeHistory_Table = "tradeHistory";


    public PortfolioDatabase(Context context) {
        super(context, "portfolioTable");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createTable = "CREATE TABLE " + MDatabasename + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PTCol1 + "INTEGER" +
                PTCol2 + "REAL" + // 8-bit floating point number
                PTCol3 + "TEXT" +
                PTCol4 + "TEXT";
        sqLiteDatabase.execSQL(createTable);
    }


}
