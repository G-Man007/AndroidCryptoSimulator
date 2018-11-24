package com.example.branden.cryptocurrencytradingsimulator;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.database.Cursor;
import android.widget.Toast;
import android.os.Handler;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.zip.DataFormatException;
import java.text.SimpleDateFormat;

public class cryptoAPI extends AppCompatActivity {
    /* I'm not sure what is going on in this file but there
    are changes added by Branden that look like they shouldn't exist.
    I am removing all Trade class related calls and leaving what is new.
     */
//TODO
//    @RequiresApi(api = Build.VERSION_CODES.O)
//    private void buy(){
//        String cryptoName = "";
//        String temp = mNumToBuy.getText().toString();
//        int numberBought = Integer.parseInt(temp);// 0;
//        double currentPrice = 0;
//
//        /*DataFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
//        Date date = new Date();
//        System.out.println(dateFormat.format(date)); //2016/11/16 12:08:43*/
//
//        //gets time and date
//        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
//        LocalDateTime now = LocalDateTime.now();//2016/11/16 12:08:43
//
//        String temp1 = dtf.format(now);
//        char[] fullDate = temp1.toCharArray();
//        String date ="";
//        date = date.copyValueOf( fullDate, 0, 9 );//The first 10 charaters are the data
//        String time="";
//        time = time.copyValueOf( fullDate, 10, 18 );//the last 8 characters are the time
//
//        boolean success = mDataBase.addDataCol(cryptoName, numberBought , currentPrice , date , time);
//
//        if(success)
//            toaster("Your trade was successful!", 1500);
//        else
//            toaster("Trade fail", 1500);
//    }
//
//    private void getTrades(){
//        Cursor data = mDataBase.getData();
//
//        ArrayList<String> listOfData = new ArrayList<String>();
//
//        while(data.moveToNext()){
//            //gets the data from the first colume
//            //adds it to arrayList
//            listOfData.add(data.getString(1));
//        }
//
//
//    }
//
//    public void toaster(String message, int length) {
//
//        final Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
//        toast.show();
//
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                toast.cancel();
//            }
//        }, length);
//    }
}
