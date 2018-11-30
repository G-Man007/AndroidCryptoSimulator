package com.example.branden.cryptocurrencytradingsimulator;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TextView;
import static com.example.branden.cryptocurrencytradingsimulator.javaCryptoCompAPI.nameConversion;

/**
 * Creates the Settings activity that is displayed to the user. Settings is currently blank as it is lower priority
 * in creation but will eventually allow the user to modify aspects of the CrpytoCurrency Simulator.
 **/
public class Test extends AppCompatActivity {

    /**
     * onCreate is the default function called when starting an activity hence "onCreate" and runs the default
     * functions required based on the activity. The Settings onCreate() function sets the view to the correct XML
     * layout, and calls {@link #configureNavigationButtons()}.
     *
     * @ccs.Pre-condition The navigation button to launch Settings.
     * @ccs.Post-condition the textview is set, and the navigation buttons are configured.
     */
    @Override
    @RequiresApi(api = Build.VERSION_CODES.O)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        boolean[] testResult = new boolean[8];


        TextView test1 = findViewById(R.id.Test1);
        test1.setText("nameConversion full name to abberviation");
        TextView test1Result = findViewById(R.id.Test1Result);
        if(nameConversion("Bitcoin").equals("BTC")){
            test1Result.setText("PASSED");
        }else{
            test1Result.setText("FAILED");
        }

        TextView test2 = findViewById(R.id.Test2);
        test2.setText("nameConversion returns 'ERROR' on bad coin input");
        TextView test2Result = findViewById(R.id.Test2Result);
        if(nameConversion("TestingName").equals("ERROR")) {
            test2Result.setText("PASSED");
        }else{
            test2Result.setText("FAILED");
        }


        TextView test3 = findViewById(R.id.Test3);
        test3.setText("initialize settingDatabase data");
        TextView test3Result = findViewById(R.id.Test3Result);
        SettingsDatabase setDatabase = new SettingsDatabase(this);
        if(setDatabase != null) {
            test3Result.setText("PASSED");
        }else{
            test3Result.setText("FAILED");
        }


        TextView test4 = findViewById(R.id.Test4);
        test4.setText("initialize PortfolioDatabase database");
        TextView test4Result = findViewById(R.id.Test4Result);
        PortfolioDatabase PortDatabase = new PortfolioDatabase(this);
        if(PortDatabase != null) {
            test4Result.setText("PASSED");
        }else{
            test4Result.setText("FAILED");
        }

        TextView test5 = findViewById(R.id.Test5);
        test5.setText("initialize TransactionDatabase database");
        TextView test5Result = findViewById(R.id.Test5Result);
        TransactionsDatabase TransDatabase = new TransactionsDatabase(this);
        if(TransDatabase != null) {
            test5Result.setText("PASSED");
        }else{
            test5Result.setText("FAILED");
        }

        TextView test6 = findViewById(R.id.Test6);
        test6.setText("initialize PortfolioHistoryDatabase database");
        TextView test6Result = findViewById(R.id.Test6Result);
        PortfolioHistoryDatabase PortHistDatabase = new PortfolioHistoryDatabase(this);
        if(PortHistDatabase != null) {
            test6Result.setText("PASSED");
        }else{
            test6Result.setText("FAILED");
        }

        TextView test7 = findViewById(R.id.Test7);
        test7.setText("initialize coin data");
        TextView test7Result = findViewById(R.id.Test7Result);
        String[][] initial = javaCryptoCompAPI.initializeCoinData("USD");
        if(initial[0][0]=="BTC") {
            test7Result.setText("PASSED");
        }else{
            test7Result.setText("FAILED");
        }

        TextView test8 = findViewById(R.id.Test8);
        test8.setText("update coin data");
        TextView test8Result = findViewById(R.id.Test8Result);
        String[][] update = javaCryptoCompAPI.initializeCoinData("USD");
        if(update[1][1]!= null) {
            test8Result.setText("PASSED");
        }else{
            test8Result.setText("FAILED");
        }

        /*TextView test9 = findViewById(R.id.Test9);
        test9.setText("weekly price data");
        TextView test9Result = findViewById(R.id.Test9Result);
        double[] weekData = javaCryptoCompAPI.weeklyPriceInfo("Bitcoin");
        if(weekData[0] != 0 ) {
            test9Result.setText("PASSED");
        }else{
            test9Result.setText("FAILED");
        }*/

        TextView test10 = findViewById(R.id.Test10);
        test10.setText("coin name list");
        TextView test10Result = findViewById(R.id.Test10Result);
        String[] names = javaCryptoCompAPI.getCoinNames();
        if(names.length == 69) {
            test10Result.setText("PASSED");
        }else{
            test10Result.setText("FAILED");
        }

        TextView test11 = findViewById(R.id.Test11);
        test11.setText("get Portfolio Data");
        TextView test11Result = findViewById(R.id.Test11Result);
        Cursor cur = PortDatabase.getData();
        if(cur != null  ) {
            test11Result.setText("PASSED");
        }else{
            test11Result.setText("FAILED");
        }

        TextView test12 = findViewById(R.id.Test12);
        test12.setText("get Portfolio History Data");
        TextView test12Result = findViewById(R.id.Test12Result);
        cur = PortHistDatabase.getData();
        if(cur != null  ) {
            test12Result.setText("PASSED");
        }else{
            test12Result.setText("FAILED");
        }

        TextView test13 = findViewById(R.id.Test13);
        test13.setText("get Transaction Data");
        TextView test13Result = findViewById(R.id.Test13Result);
        cur = TransDatabase.getData();
        if(cur != null  ) {
            test13Result.setText("PASSED");
        }else{
            test13Result.setText("FAILED");
        }

        /*TextView test14 = findViewById(R.id.Test14);
        test8.setText("get Portfolio Data");
        TextView test14Result = findViewById(R.id.Test14Result);
        cur = PortDatabase.getData();
        if(cur.getString(1) != null  ) {
            test14Result.setText("PASSED");
        }else{
            test14Result.setText("FAILED");
        }*/
        configureNavigationButtons();
    }

    /**
     * configureNavigationButtons() sets each of the Button XML elements click listeners to their corresponding button
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
                startActivity(new Intent(Test.this, Home.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        Button searchButton = findViewById(R.id.searchBtn);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Test.this, Search.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        Button settingsButton = findViewById(R.id.settingsBtn);
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Test.this, Settings.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });
    }
}
