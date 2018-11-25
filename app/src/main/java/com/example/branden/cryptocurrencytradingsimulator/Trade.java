package com.example.branden.cryptocurrencytradingsimulator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.GraphView;

/**
* Creates the Trade activity that is displayed to the user, currently displayed as a navigation button for
* testing purposes but will be implemented as the default window for selecting a crypto-currency.
* */
public class Trade extends AppCompatActivity {

  private TextView mTextMessage;

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

    TextView name = (TextView)findViewById(R.id.name);
    name.setText(getIntent().getStringExtra("name"));

    GraphView graph = (GraphView) findViewById(R.id.tradeGraph);
    LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
      new DataPoint(0, 6),
      new DataPoint(1, 3),
      new DataPoint(2, 4),
      new DataPoint(3, 4),
      new DataPoint(4, 2)
    });
    graph.addSeries(series);

    mTextMessage = (TextView) findViewById(R.id.message);
    configureNavigationButtons();
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
}
