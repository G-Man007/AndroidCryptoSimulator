package com.example.branden.cryptocurrencytradingsimulator;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.LineGraphSeries;

import static com.example.branden.cryptocurrencytradingsimulator.javaCryptoCompAPI.currencyChosen;
import static com.example.branden.cryptocurrencytradingsimulator.javaCryptoCompAPI.initializeCoinData;
import static com.example.branden.cryptocurrencytradingsimulator.javaCryptoCompAPI.updateCoinData;

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
        configureUpdateBtn();
        configureGraph();
        configureNavigationButtons();
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
}
