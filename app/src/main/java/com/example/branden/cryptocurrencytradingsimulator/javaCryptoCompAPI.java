package com.example.branden.cryptocurrencytradingsimulator;
//imports needed to connect to API
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class javaCryptoCompAPI {

    private static String mCoinData[][];

/**
 * This function initializes all of the coin prices {@link Home} {@link search} {@link trade} will use all of this pricing info
 * when the app launches, also called when @see #update()
 *
 * @ms.Pre-condition Function can be called at any time updated prices are wanted for all of the coins
 * @ms.Post-condition Will initialize a base data structure to pull from
 *
 * @see #mCoinData
 *
 **/
    static void initializeCoinData() {
            String coinPrices = null;
            try {
                String url = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH,XRP,BCH,XLM,EOS,LTC,USDT,ADA,BNB,XMR,TRX,IOT,DASH,NEO,ETC,ZRX,XEM,ZEC,VEN,DOGE,BTG,QTUM,AE,LINK,BAT,DCR,XRB,LSK,ICX,DGB,BTS,SNT,SC,QASH,XVG,WTC,GNO,WAVES,PPT,ETP,GNT,FUN,LRC,STORJ,KMD,STRAT,MCO,CVC,REP,PAY,ARDR,XUC,BNT,DGD,KNC,MAID,SALT,HSR,ARK,STEEM,PIVX,MONA,DCN,ZEN,SUB,VERI,NXT,SYS,GAS&tsyms=USD,EUR,JPY,GBP,CHF,CAD,AUD,HKD,SEK,MXN,NZD,SGD,NOK,KRW,TRY,INR,RUB,BRL,ZAR,PEN,ZOF,UGX,EGP,MGA,COP&extraParams=cryptoSimulatorSchoolProject";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                /* Left this chunk in for testing purposes
                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);
                */

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response;
                response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                } in .close();
                coinPrices = response.toString();
                //print in String
                // System.out.println(coinPrices);
            } catch(Exception e) {
                System.out.println(e);
            }

            //TODO: insert Java parse code to use split or to use org.json library splitter
            //TODO: Place parsed code into 2d array structure, best for memory and efficiency
            //String to parse is coinPrices, into Array mCoinData[][]
            //String [] crytSplitter = coinPrices.split("{}",-1);

    }

/**
 * This function is called in {@link search} when a user wants to search for
 * a certain coins statistics
 *
 * @ms.Pre-condition Passes in
 * @ms.Post-condition UI has full access to a coins stats
 *
 * @param coin is a string used for searching
 * @return array filled with coin statistics
 **/
    static void searchCrypto(String coin) {
            String coinPrices = null;
            try {
                String url = "https://min-api.cryptocompare.com/data/pricemulti?fsyms="+coin+"&tsyms=USD,EUR,JPY,GBP&extraParams=cryptoSimulatorSchoolProject";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                /* Left this chunk in for testing purposes
                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);
                */

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                } in .close();
                coinPrices = response.toString();
                //print price to console-testing
                System.out.println(coinPrices);
            } catch(Exception e) {
                System.out.println(e);
            }

            //TODO: insert Java parse code

    }

/**
 * This function defers from the previous as it returns a full array of statistics.
 * Will be used by {@link trade}
 *
 *
 * @ms.Pre-condition needs a coin string to get statistics from
 * @ms.Post-condition Returns the advanced statistics
 *
 * @param coin is a string used for searching
 *
 * */
    static void cryptoAdvancedStats(String coin) {

        String coinStats = null;

            try {
                String url = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms="+coin+"&tsyms=USD&extraParams=cryptoSimulatorSchoolProject";
                URL obj = new URL(url);
                HttpURLConnection con = (HttpURLConnection) obj.openConnection();

                /* Left this chunk in for testing purposes
                int responseCode = con.getResponseCode();
                System.out.println("\nSending 'GET' request to URL : " + url);
                System.out.println("Response Code : " + responseCode);
                */

                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                } in .close();
                coinStats = response.toString();
                //print price to console-testing
                System.out.println(coinStats);
            } catch(Exception e) {
                System.out.println(e);
            }

            //TODO: insert Java parse code, String Stats
            //TODO: In advanced we have raw and display blocks for information
    }

/**
 * Function used in {@link Home} to update the coin prices in real time
 *
 * @ms.Pre-condition called when update is requested
 * @ms.Post-condition Updates coin prices to real time
 *
 * */
    static void update(){
        initializeCoinData();
    }

}

