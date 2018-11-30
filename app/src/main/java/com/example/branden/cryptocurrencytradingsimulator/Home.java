package com.example.branden.cryptocurrencytradingsimulator;
import java.text.NumberFormat;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.*;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.branden.cryptocurrencytradingsimulator.javaCryptoCompAPI.*;

/**
 * Creates the Home activity that is displayed to the user on boot which houses the portfolio and graph related to
 * the portfolio.
 */
public class Home extends AppCompatActivity {

    /**
     * onCreate is the default function called when starting an activity hence "onCreate" and runs the default
     * functions required based on the activity. The Home onCreate() function creates the graph, sets the view to the
     * correct XML layout, and calls {@link #configureNavigationButtons()}.
     *
     * @ccs.Pre-condition The navigation button to launch Home has been pressed or the app has just been booted.
     * @ccs.Post-condition The graphs are created, the textview is set, and the navigation buttons are configured
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        setContentView(R.layout.activity_home);
        initializeCoinData(currencyChosen);
        initializeSharedPref();
        configureUpdateBtn();
        configureNavigationButtons();
        displayHome();
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayHome();
    }

    private void configureGraph() {

        GraphView graph = findViewById(R.id.homeGraph);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        graph.getGridLabelRenderer().setHumanRounding(false);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[]{
                new DataPoint(1, 802.20),
                new DataPoint(2, 207.90),
                new DataPoint(3, .46)
        });
        graph.addSeries(series);

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[]{
                new DataPoint(1, 1010.56),
                new DataPoint(2, 1010.56),
                new DataPoint(3, 1010.56)
        });
        graph.addSeries(series2);

        staticLabelsFormatter.setHorizontalLabels(new String[]{"CEFS", "Ethereum", "Ripple"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
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
        Button settingsButton = findViewById(R.id.settingsBtn);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Settings.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        Button searchButton = findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Search.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }

    /**
     * Sets the update button to call the appropriate function defined in {@link javaCryptoCompAPI}
     * that updates the coin information.
     *
     * @ccs.Pre-condition {@link #onCreate(Bundle)} is called.
     * @ccs.Post-condition Update button is configured to the appropriate function call.
     */
    private void configureUpdateBtn() {
        Button updateButton = findViewById(R.id.btnUpdate);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateCoinData();
            }
        });
    }

    private void initializeSharedPref(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(Home.this);
        if(!prefs.contains("initialized")){
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("initialized", 1);
            editor.putString("money", "100000");
            String[] names = getCoinNames();
            for(int i = 0; i < names.length; i++){
                editor.putInt(names[i], 0);
            }
            editor.apply();
        }
    }

    private void displayHome(){
        configureGraph();
        List<String> populateList = new ArrayList<>();
        List<Integer> quantityList = new ArrayList<>();
        String[] cryptoNames = javaCryptoCompAPI.getCoinNames();
        ListView listDisplay = findViewById(R.id.listDisplay);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        TextView portfolio = findViewById(R.id.textPortfolio);
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        String moneyString = formatter.format(Double.parseDouble(prefs.getString("money", "DEFAULT")));
        portfolio.setText(moneyString);

        for(int i = 0; i < cryptoNames.length; i++){
            if(prefs.getInt(cryptoNames[i], -1) > 0){
                populateList.add(cryptoNames[i]);
                quantityList.add(prefs.getInt(cryptoNames[i],-1));
            }
        }
        String[] displayedCoins = new String[populateList.size()];
        displayedCoins = populateList.toArray(displayedCoins);
        String[] displayedPrices = new String[displayedCoins.length];

        String[] displayedQuantities = new String[displayedCoins.length];

        for(int i = 0; i < displayedPrices.length; i++){
            displayedPrices[i] = search(nameConversion(displayedCoins[i]))[1];
            displayedQuantities[i] = Integer.toString((prefs.getInt(displayedCoins[i], -1)));
        }

        String[] finalDisplay = new String[populateList.size()];
        for(int i = 0; i < displayedPrices.length; i++){
            finalDisplay[i] = String.format("%-10s %15s %20s",nameConversion(displayedCoins[i]),displayedQuantities[i],displayedPrices[i]);
        }

        ArrayAdapter<String> adapterDisplay = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, finalDisplay);

        listDisplay.setAdapter(adapterDisplay);
    }
}
