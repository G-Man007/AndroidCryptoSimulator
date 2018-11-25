package com.example.branden.cryptocurrencytradingsimulator;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import java.util.Vector;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    String MDatabasename;

    public DatabaseHelper(Context context, String databaseName) {
        super(context, databaseName, null, 1);
        MDatabasename = databaseName;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP IF TABLE EXISTS " + MDatabasename);
        onCreate(sqLiteDatabase);
    }

    public boolean addStrData(String tableName, String columnName, String strValue ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues ContentValue = new ContentValues();
        long result;

        ContentValue.put(columnName, strValue);
        result = db.insert(columnName, null , ContentValue);
        if(result == -1)
            return false;

        //Log.d(TAG, "addData: Adding " + PTCol1 + "to " + Portfolio_Table);

        return true;
    }

    public boolean addIntData(String tableName, String columnName, int intValue ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues ContentValue = new ContentValues();
        long result;

        ContentValue.put(columnName, intValue);
        result = db.insert(columnName, null , ContentValue);
        if(result == -1)
            return false;

        return true;
    }

    public boolean addRealData(String tableName, String columnName, double realValue ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues ContentValue = new ContentValues();
        long result;

        ContentValue.put(columnName, realValue);
        result = db.insert(columnName, null , ContentValue);
        if(result == -1)
            return false;

        return true;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * From " + MDatabasename;
        Cursor data = db.rawQuery(query, null);

        return data;
    }
}
