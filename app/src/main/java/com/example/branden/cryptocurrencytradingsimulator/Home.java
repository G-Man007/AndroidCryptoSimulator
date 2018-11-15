package com.example.branden.cryptocurrencytradingsimulator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.LineGraphSeries;

public class Home extends AppCompatActivity {

    private TextView mTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);



        GraphView graph = (GraphView) findViewById(R.id.homeGraph);
        StaticLabelsFormatter staticLabelsFormatter = new StaticLabelsFormatter(graph);
        graph.getGridLabelRenderer().setHumanRounding(false);
        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 802.20),
                new DataPoint(2, 207.90),
                new DataPoint(3, .46)
        });
        graph.addSeries(series);

        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(1, 1010.56),
                new DataPoint(2, 1010.56),
                new DataPoint(3, 1010.56)
        });
        graph.addSeries(series2);

        staticLabelsFormatter.setHorizontalLabels(new String[]{ "CEFS","Ethereum", "Ripple"});
        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);

        mTextMessage = (TextView) findViewById(R.id.message);
        configureNavigationButtons();
    }

    private void configureNavigationButtons(){
        Button tradeButton = (Button) findViewById(R.id.tradeBtn);
        tradeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Trade.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        Button settingsButton = (Button) findViewById(R.id.settingsBtn);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Settings.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        Button searchButton = (Button) findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Home.this, Search.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }
}
