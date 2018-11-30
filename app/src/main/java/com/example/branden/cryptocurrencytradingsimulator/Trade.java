package com.example.branden.cryptocurrencytradingsimulator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.GraphView;
import android.widget.Toast;
import android.os.Handler;
import java.util.Calendar;
import java.util.Date;

import static com.example.branden.cryptocurrencytradingsimulator.javaCryptoCompAPI.nameConversion;
import static com.example.branden.cryptocurrencytradingsimulator.javaCryptoCompAPI.search;
import static com.example.branden.cryptocurrencytradingsimulator.javaCryptoCompAPI.weeklyPriceInfo;

/**
 * Creates the Trade activity that is displayed to the user, currently displayed as a navigation button for
 * testing purposes but will be implemented as the default window for selecting a crypto-currency.
 */
public class Trade extends AppCompatActivity {
    /**
     * The default function called when starting an activity hence "onCreate" and runs the default
     * functions required based on the activity. The Trade onCreate() function creates the graphs, sets the view to the
     * correct XML layout, and calls {@link #configureNavigationButtons()}.
     *
     * @ccs.Pre-condition The navigation button to launch Trade has been pressed.
     * @ccs.Post-condition The graphs are created, the textview is set, and the navigation buttons are configured
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trade);
        TextView name = findViewById(R.id.name);
        String cryptoName = getIntent().getStringExtra("name");
        name.setText(cryptoName);

        String[] tradeInfo = search(cryptoName);

        TextView value = findViewById(R.id.value);
        value.setText(tradeInfo[1]);
        TextView open = findViewById(R.id.open);
        open.setText(tradeInfo[3]);
        TextView low = findViewById(R.id.low);
        low.setText(tradeInfo[5]);
        TextView high = findViewById(R.id.high);
        high.setText(tradeInfo[4]);
        TextView mktCap = findViewById(R.id.mktCap);
        mktCap.setText(tradeInfo[6]);
        TextView supply = findViewById(R.id.supply);
        supply.setText(tradeInfo[7]);
        TextView tVol = findViewById(R.id.tVol);
        tVol.setText(tradeInfo[8]);
        TextView fVol = findViewById(R.id.fVol);
        fVol.setText(tradeInfo[9]);
        TextView pctChng24 = findViewById(R.id.pctChng24);
        pctChng24.setText(tradeInfo[11]);
        TextView chng24hr = findViewById(R.id.chng24hr);
        chng24hr.setText(tradeInfo[12]);

        configureGraph(cryptoName);
        configureNavigationButtons();
        configureBuySellButtons();
    }

    /**
     * Creates the graph showing the information for the coins price of Today and the past six days.
     *
     * @ccs.Pre-condition {@link #onCreate(Bundle)} is called.
     * @ccs.Post-condition Graph is created related to the clicked on coin.
     */
    private void configureGraph(String currency) {
        GraphView graph = findViewById(R.id.tradeGraph);

        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, -1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, -1);
        Date d3 = calendar.getTime();
        calendar.add(Calendar.DATE, -1);
        Date d4 = calendar.getTime();
        calendar.add(Calendar.DATE, -1);
        Date d5 = calendar.getTime();
        calendar.add(Calendar.DATE, -1);
        Date d6 = calendar.getTime();
        calendar.add(Calendar.DATE, -1);
        Date d7 = calendar.getTime();

        double[] prices = weeklyPriceInfo(currency);

        TextView currentPrice = findViewById(R.id.value);
        String currentValue = Double.toString(prices[0]);
        currentPrice.setText('$' + currentValue);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(d7, prices[6]),
                new DataPoint(d6, prices[5]),
                new DataPoint(d5, prices[4]),
                new DataPoint(d4, prices[3]),
                new DataPoint(d3, prices[2]),
                new DataPoint(d2, prices[1]),
                new DataPoint(d1, prices[0])
        });
        graph.addSeries(series);

        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(Trade.this));
        graph.getGridLabelRenderer().setNumHorizontalLabels(7);
        graph.getGridLabelRenderer().setHorizontalLabelsAngle(90);
        graph.getGridLabelRenderer().setLabelsSpace(15);

        graph.getViewport().setMinX(d7.getTime());
        graph.getViewport().setMaxX(d1.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

        graph.getGridLabelRenderer().setHumanRounding(false);
    }

    /**
     * Takes the current price of the crypto and stores it in the database as a transaction
     *
     * @ccs.Pre-condition {@link #onCreate(Bundle)} is called. buyPower is needed to be larger than the amount
     *                     that is being bought.
     * @ccs.Post-condition Stack is cleared to any previous instance of desired activity, activity is then launched.
     */
    private void buy() {
        TextView quantity = findViewById(R.id.etQuantity);
        int userInput;
        try{
            userInput = Integer.parseInt(quantity.getText().toString());
            if(userInput == 0){
                toaster("No changes were made.", 3000);
                return;
            } else if(userInput < 0){
                toaster("Negative values are not valid.", 3000);
                return;
            }
            String cryptoName = getIntent().getStringExtra("name");
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Trade.this);
            SharedPreferences.Editor editor = sharedPref.edit();

            if(Double.parseDouble(sharedPref.getString("money","DEFAULT")) >=  (Double.parseDouble(search(nameConversion(cryptoName))[1].replaceAll("[^\\d.]+", "")) * userInput)){
                int currentQuantity = sharedPref.getInt(cryptoName, 0);
                int newQuantity = currentQuantity + userInput;
                Double newMoney = Double.parseDouble(sharedPref.getString("money","DEFAULT")) - (Double.parseDouble(search(nameConversion(cryptoName))[1].replaceAll("[^\\d.]+", "")) * userInput);
                editor.putInt(cryptoName, newQuantity);
                editor.putString("money",Double.toString(newMoney));
                editor.apply();
                String purchaseMessage = "Purchase successful, new quantity is: " + Integer.toString(newQuantity);
                toaster(purchaseMessage, 3000);
            }else{
                toaster("Not enough currency to make purchase.", 3000);
            }
        } catch(Exception e){
            toaster("Invalid input!", 3000);
        }
    }

    /**
     * Removes the amount from the portfolio and adds the quantity multiplied by its sell value
     * and adds it onto the user's buying power
     *
     * @ccs.Pre-condition {@link #onCreate(Bundle)} is called. The user has the necessary amount owned.
     * @ccs.Post-condition Stack is cleared to any previous instance of desired activity, activity is then launched.
     */
    private void sell() {
        TextView quantity = findViewById(R.id.etQuantity);
        int userInput;
        try{
            userInput = Integer.parseInt(quantity.getText().toString());
            if(userInput == 0){
                toaster("No changes were made.", 3000);
                return;
            } else if(userInput < 0){
                toaster("Negative values are not valid.", 3000);
                return;
            }
            String cryptoName = getIntent().getStringExtra("name");
            SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(Trade.this);
            SharedPreferences.Editor editor = sharedPref.edit();

            if(sharedPref.getInt(cryptoName,-1) >= userInput){
                int currentQuantity = sharedPref.getInt(cryptoName, -1);
                int newQuantity = currentQuantity - userInput;
                Double newMoney = Double.parseDouble(sharedPref.getString("money","DEFAULT")) + (Double.parseDouble(search(nameConversion(cryptoName))[1].replaceAll("[^\\d.]+", "")) * userInput);
                editor.putInt(cryptoName, newQuantity);
                editor.putString("money",Double.toString(newMoney));
                editor.apply();
                String purchaseMessage = "Sell successful, new quantity is: " + Integer.toString(newQuantity);
                toaster(purchaseMessage, 3000);
                String newTotal = "New total money is: " + Double.toString(newMoney);
                toaster(newTotal, 3000);
            }else{
                toaster("Not enough coins available to make sell.", 3000);
            }
        } catch(Exception e){
            toaster("Invalid input!", 3000);
        }
    }

    /**
     * Sets each of the Button XML elements click listeners to their corresponding button
     * in which they will startActivity the proper activity with the FLAG_ACTIVITY_CLEAR_TOP flag in order to clear
     * the stack down to any previous instances of the desired activity.
     *
     * @ccs.Pre-condition {@link #onCreate(Bundle)} is called.
     * @ccs.Post-condition Stack is cleared to any previous instance of desired activity, activity is then launched.
     */
    private void configureNavigationButtons() {
        Button homeButton = findViewById(R.id.homeBtn);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Trade.this, Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        Button settingsButton = findViewById(R.id.settingsBtn);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Trade.this, Settings.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        Button searchButton = findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Trade.this, Search.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    private void configureBuySellButtons(){
        Button buyButton = findViewById(R.id.buyBtn);
        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buy();
            }
        });

        Button sellButton = findViewById(R.id.sellBtn);
        sellButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sell();
            }
        });
    }
    /**
     * Takes in a string and an integer and outputs a display that lasts for how long the integer is
     * based on milliseconds.
     *
     * @ccs.Pre-condition {@link #onCreate(Bundle)} is called.
     * @ccs.Post-condition A display will appear with the inputted message and lasts for the inputted length.
     */
    private void toaster(String message, int length) {

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
