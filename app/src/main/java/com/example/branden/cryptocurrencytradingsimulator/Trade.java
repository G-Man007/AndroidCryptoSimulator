package com.example.branden.cryptocurrencytradingsimulator;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.GraphView;
import android.widget.Toast;
import android.os.Handler;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.zip.DataFormatException;
import java.text.SimpleDateFormat;
import java.util.Vector;

/**
 * Creates the Trade activity that is displayed to the user, currently displayed as a navigation button for
 * testing purposes but will be implemented as the default window for selecting a crypto-currency.
 * */
public class Trade extends AppCompatActivity {

    public TransacationsDatabase dbTransacation;
    public PortfolioHistoryDatabase dbhistory;
    public SettingsDatabase dbSettings;

    TextView cyptoName;
    EditText quantity;

    /**
     * onCreate is the default function called when starting an activity hence "onCreate" and runs the default
     * functions required based on the activity. The Trade onCreate() function creates the graphs, sets the view to the
     * correct XML layout, and calls {@link #configureNavigationButtons()}.
     *
     * @ccs.Pre-condition The navigation button to launch Trade has been pressed.
     * @ccs.Post-condition The graphs are created, the textview is set, and the navigation buttons are configured
     * */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);

        cyptoName = (TextView) findViewById(R.id.tvName);
        quantity = (EditText) findViewById(R.id.etQuantity);

        GraphView graph = (GraphView) findViewById(R.id.tradeGraph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 6),
                new DataPoint(1, 3),
                new DataPoint(2, 4),
                new DataPoint(3, 4),
                new DataPoint(4, 2)
        });
        graph.addSeries(series);
        configureNavigationButtons();
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void buy(){
        String temp = quantity.getText().toString();
        int quant = Integer.parseInt(temp);
        int bPrice = 0;//buy price
        javaCryptoCompAPI apiData = new javaCryptoCompAPI();
        String name = cyptoName.getText().toString();
        String time = "";
        String date = "";

        String[] cyptoData = apiData.search(name);
        bPrice = Integer.parseInt(cyptoData[1]);

        //get buying power to see if play has enough funds
        Vector<Double> temp1 =  dbSettings.getBuyingPower();
        double buyPower = temp1.get(0);

        if( buyPower < (quant * bPrice)){
            toaster("You do not have enough buying power to execute this trade", 1500);
            return;
        }

        //update portfolio value
        buyPower -= (quant * bPrice);
        dbTransacation.addRealData("settingsDatabase", "buyingPower", buyPower);

        //gets time and date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();//2016/11/16 12:08:43

        String temp2 = dtf.format(now);
        char[] fullDate = temp2.toCharArray();
        date = date.copyValueOf( fullDate, 0, 9 );//The first 10 charaters are the data
        time = time.copyValueOf( fullDate, 10, 18 );//the last 8 characters are the time


        //put data in database
        //tell user fail if unsuccessful and return
        boolean success = false;
        success = dbTransacation.addStrData("transacationsDatabase", "name", name);
        if(success == false) {toaster("Trade fail", 1500); return;}
        success = dbTransacation.addIntData("transacationsDatabase", "transacationsType", 1);
        if(success == false) {toaster("Trade fail", 1500); return;}
        success = dbTransacation.addIntData("transacationsDatabase", "quantity", quant);
        if(success == false) {toaster("Trade fail", 1500); return;}
        success = dbTransacation.addRealData("transacationsDatabase", "price", bPrice);
        if(success == false) {toaster("Trade fail", 1500); return;}

        toaster("Your trade was successful!", 1500);
    }

    private void sell(){
        
    }

    /**
     * configureNavigationButtons() sets each of the Button XML elements click listeners to their corresponding button
     * in which they will startActivity the proper activity with the FLAG_ACTIVITY_CLEAR_TOP flag in order to clear
     * the stack down to any previous instances of the desired activity.
     *
     * @ccs.Pre-condition {@link #onCreate(Bundle)} is called.
     * @ccs.Post-condition Stack is cleared to any previous instance of desired activity, activity is then launched.
     * */
    private void configureNavigationButtons(){
        Button homeButton = (Button) findViewById(R.id.homeBtn);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Trade.this, Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        Button settingsButton = (Button) findViewById(R.id.settingsBtn);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Trade.this, Settings.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        Button searchButton = (Button) findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Trade.this, Search.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    public void toaster(String message, int length){

        final Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toast.cancel();
            }
        }, length);
    }
}
