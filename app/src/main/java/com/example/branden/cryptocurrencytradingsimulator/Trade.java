package com.example.branden.cryptocurrencytradingsimulator;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import static com.example.branden.cryptocurrencytradingsimulator.javaCryptoCompAPI.weeklyPriceInfo;

/**
 * Creates the Trade activity that is displayed to the user, currently displayed as a navigation button for
 * testing purposes but will be implemented as the default window for selecting a crypto-currency.
 */
public class Trade extends AppCompatActivity {
    private TransactionsDatabase dbTransacation;
    private PortfolioDatabase dbPortfolio;
    private PortfolioHistoryDatabase dbhistory;
    private SettingsDatabase dbSettings;

    private TextView cryptoName;
    private EditText quantity;

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
        quantity = findViewById(R.id.etQuantity);
        String cryptoName = getIntent().getStringExtra("name");
        name.setText(cryptoName);

        String[] tradeInfo = javaCryptoCompAPI.search(cryptoName);

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
        String currentValue = Double.toString(prices[6]);
        currentPrice.setText('$' + currentValue);

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(d7, prices[0]),
                new DataPoint(d6, prices[1]),
                new DataPoint(d5, prices[2]),
                new DataPoint(d4, prices[3]),
                new DataPoint(d3, prices[4]),
                new DataPoint(d2, prices[5]),
                new DataPoint(d1, prices[6])
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void buy() {
        String temp = quantity.getText().toString();
        int quantity = Integer.parseInt(temp);
        int bPrice = 0;//buy price
        javaCryptoCompAPI apiData = new javaCryptoCompAPI();
        String name = cryptoName.getText().toString();
        String time = "";
        String date = "";

        String[] cryptoData = apiData.search(name);
        bPrice = Integer.parseInt(cryptoData[1]);

        //get buying power to see if play has enough funds
        Vector<Double> temp1 = dbSettings.getBuyingPower();
        double buyPower = temp1.get(0);

        if (buyPower < (quantity * bPrice)) {
            toaster("You do not have enough buying power to execute this trade", 1500);
            return;
        }

        //update portfolio value
        buyPower -= (quantity * bPrice);
        dbTransacation.addRealData("settingsDatabase", "buyingPower", buyPower);

        //gets time and date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();//2016/11/16 12:08:43

        String temp2 = dtf.format(now);
        char[] fullDate = temp2.toCharArray();
        date = date.copyValueOf(fullDate, 0, 9);//The first 10 charaters are the data
        time = time.copyValueOf(fullDate, 10, 18);//the last 8 characters are the time


        //put data in database
        //tell user fail if unsuccessful and return
        boolean success;
        success = dbTransacation.addStrData("transacationsDatabase", "name", name);
        if (!success) {
            toaster("Trade fail", 1500);
            return;
        }
        success = dbTransacation.addIntData("transacationsDatabase", "transacationsType", 1);
        if (!success) {
            toaster("Trade fail", 1500);
            return;
        }
        success = dbTransacation.addIntData("transacationsDatabase", "quantity", quantity);
        if (!success) {
            toaster("Trade fail", 1500);
            return;
        }
        success = dbTransacation.addRealData("transacationsDatabase", "price", bPrice);
        if (!success) {
            toaster("Trade fail", 1500);
            return;
        }

        dbPortfolio.addIntData("portfolioTable", "quantity", quantity);
        dbPortfolio.addRealData("portfolioTable", "buyPrice", bPrice);
        dbPortfolio.addStrData("portfolioTable", "buyTime", time);
        dbPortfolio.addStrData("portfolioTable", "buyDate", date);
        dbPortfolio.addStrData("portfolioTable", "name", name);


        toaster("Your trade was successful!", 1500);
    }

    /**
     * Removes the amount from the portfolio and adds the quantity multiplied by its sell value
     * and adds it onto the user's buying power
     *
     * @ccs.Pre-condition {@link #onCreate(Bundle)} is called. The user has the necessary amount owned.
     * @ccs.Post-condition Stack is cleared to any previous instance of desired activity, activity is then launched.
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sell() {
        String temp = quantity.getText().toString();
        int quantity = Integer.parseInt(temp);
        int sPrice = 0;//sell price
        javaCryptoCompAPI apiData = new javaCryptoCompAPI();
        String name = cryptoName.getText().toString();
        String time = "";
        String date = "";

        Vector<String> ownedCrypto = dbPortfolio.getName();
        Vector<Integer> quantities = dbPortfolio.getQuantity();

        boolean validTrade = false;

        //checks if the crypto to sell is owned and enough is owned
        for (int x = 0; x < ownedCrypto.size(); x++) {
            if (ownedCrypto.get(x).equals(name)) {
                if (quantities.get(x) >= quantity) {
                    validTrade = true;
                }
            }
        }

        if (!validTrade) {
            toaster("You don't own enough of this crypto to execute this trade", 1500);
            return;
        }

        //remove crypto from protfolio database

        //get crypto price from API
        String[] cryptoData = apiData.search(name);
        sPrice = Integer.parseInt(cryptoData[1]);

        //get current buying power from database
        Vector<Double> temp1 = dbSettings.getBuyingPower();
        double buyPower = temp1.get(0);

        //update portfolio value
        buyPower += (quantity * sPrice);
        dbTransacation.addRealData("settingsDatabase", "buyingPower", buyPower);

        //gets time and date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();//2016/11/16 12:08:43

        String temp2 = dtf.format(now);
        char[] fullDate = temp2.toCharArray();
        date = date.copyValueOf(fullDate, 0, 9);//The first 10 charaters are the data
        time = time.copyValueOf(fullDate, 10, 18);//the last 8 characters are the time


        //put data in database
        //tell user fail if unsuccessful and return
        boolean success;
        success = dbTransacation.addStrData("transacationsDatabase", "name", name);
        if (!success) {
            toaster("Trade fail", 1500);
            return;
        }
        success = dbTransacation.addIntData("transacationsDatabase", "transacationsType", 0);
        if (!success) {
            toaster("Trade fail", 1500);
            return;
        }
        success = dbTransacation.addIntData("transacationsDatabase", "quantity", quantity);
        if (!success) {
            toaster("Trade fail", 1500);
            return;
        }
        success = dbTransacation.addRealData("transacationsDatabase", "price", sPrice);
        if (!success) {
            toaster("Trade fail", 1500);
            return;
        }

        toaster("Your trade was successful!", 1500);
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
