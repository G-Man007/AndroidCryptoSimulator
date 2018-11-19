package com.example.branden.cryptocurrencytradingsimulator;
//imports needed to connect to API
import java.io.BufferedReader;
import java.time.Instant;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class javaCryptoCompAPI {

    private static String currencyChosen = "USD";
    private static String[][] mCoinData = {
            {"BTC","","","","","","","","","","","","",""},{"ETH","","","","","","","","","","","","",""},{"XRP","","","","","","","","","","","","",""},{"BCH","","","","","","","","","","","","",""},{"XLM","","","","","","","","","","","","",""},{"EOS","","","","","","","","","","","","",""},{"LTC","","","","","","","","","","","","",""},{"USDT","","","","","","","","","","","","",""},{"ADA","","","","","","","","","","","","",""},{"XMR","","","","","","","","","","","","",""},
            {"TRX","","","","","","","","","","","","",""},{"IOT","","","","","","","","","","","","",""},{"DASH","","","","","","","","","","","","",""},{"NEO","","","","","","","","","","","","",""},{"ETC","","","","","","","","","","","","",""},{"ZRX","","","","","","","","","","","","",""},{"XEM","","","","","","","","","","","","",""},{"ZEC","","","","","","","","","","","","",""},{"VEN","","","","","","","","","","","","",""},{"DOGE","","","","","","","","","","","","",""},
            {"BTG","","","","","","","","","","","","",""},{"QTUM","","","","","","","","","","","","",""},{"AE","","","","","","","","","","","","",""},{"LINK","","","","","","","","","","","","",""},{"BAT","","","","","","","","","","","","",""},{"DCR","","","","","","","","","","","","",""},{"XRB","","","","","","","","","","","","",""},{"LSK","","","","","","","","","","","","",""},{"ICX","","","","","","","","","","","","",""},{"DGB","","","","","","","","","","","","",""},
            {"BTS","","","","","","","","","","","","",""},{"SNT","","","","","","","","","","","","",""},{"SC","","","","","","","","","","","","",""},{"QASH","","","","","","","","","","","","",""},{"XVG","","","","","","","","","","","","",""},{"WTC","","","","","","","","","","","","",""},{"GNO","","","","","","","","","","","","",""},{"WAVES","","","","","","","","","","","","",""},{"PPT","","","","","","","","","","","","",""},{"ETP","","","","","","","","","","","","",""},
            {"GNT","","","","","","","","","","","","",""},{"FUN","","","","","","","","","","","","",""},{"LRC","","","","","","","","","","","","",""},{"STORJ","","","","","","","","","","","","",""},{"KMD","","","","","","","","","","","","",""},{"STRAT","","","","","","","","","","","","",""},{"MCO","","","","","","","","","","","","",""},{"CVC","","","","","","","","","","","","",""},{"REP","","","","","","","","","","","","",""},{"PAY","","","","","","","","","","","","",""},
            {"ARDR","","","","","","","","","","","","",""},{"XUC","","","","","","","","","","","","",""},{"BNT","","","","","","","","","","","","",""},{"DGD","","","","","","","","","","","","",""},{"KNC","","","","","","","","","","","","",""},{"MAID","","","","","","","","","","","","",""},{"SALT","","","","","","","","","","","","",""},{"HSR","","","","","","","","","","","","",""},{"ARK","","","","","","","","","","","","",""},{"STEEM","","","","","","","","","","","","",""},
            {"PIVX","","","","","","","","","","","","",""},{"MONA","","","","","","","","","","","","",""},{"DCN","","","","","","","","","","","","",""},{"ZEN","","","","","","","","","","","","",""},{"SUB","","","","","","","","","","","","",""},{"VERI","","","","","","","","","","","","",""},{"NXT","","","","","","","","","","","","",""},{"SYS","","","","","","","","","","","","",""},{"GAS","","","","","","","","","","","","",""}
    };

    /**
     * This function initializes all of the coin prices {@link Home} {@link Trade} {@link Search} will use all of this pricing info
     * when the app launches, also called when @see #update()
     *
     * @ms.Pre-condition Function can be called at any time updated prices are wanted for all of the coins
     * @ms.Post-condition Will initialize a base data structure to pull from
     *
     * @see #mCoinData
     *
     **/
    static String[][] initializeCoinData(String currency) {
        currencyChosen = currency;
        String coinPrices = null;
        try {
            String url = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=BTC,ETH,XRP,BCH,XLM,EOS,LTC,USDT,ADA,BNB,XMR,TRX,IOT,DASH,NEO,ETC,ZRX,XEM,ZEC,VEN,DOGE,BTG,QTUM,AE,LINK,BAT,DCR,XRB,LSK,ICX,DGB,BTS,SNT,SC,QASH,XVG,WTC,GNO,WAVES,PPT,ETP,GNT,FUN,LRC,STORJ,KMD,STRAT,MCO,CVC,REP,PAY,ARDR,XUC,BNT,DGD,KNC,MAID,SALT,HSR,ARK,STEEM,PIVX,MONA,DCN,ZEN,SUB,VERI,NXT,SYS,GAS&tsyms="+currency+"&extraParams=cryptoSimulatorSchoolProject";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer coinData;
            coinData = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                coinData.append(inputLine);
            } in.close();
            coinPrices = coinData.toString();
            //print in String
            // System.out.println(coinPrices);
        } catch(Exception e) {
            System.out.println(e);
        }
try {
        JSONObject coins = new JSONObject(coinPrices);
    for (int i = 0; i < 69; i++) {

        JSONObject raw = coins.getJSONObject("RAW").getJSONObject(mCoinData[i][0]).getJSONObject(currencyChosen);
        JSONObject display = coins.getJSONObject("DISPLAY").getJSONObject(mCoinData[i][0]).getJSONObject(currencyChosen);

        String PRICE = raw.get("PRICE").toString();
        String LASTUPDATE = raw.get("LASTUPDATE").toString();
        String OPENDAY = raw.get("OPENDAY").toString();
        String HIGHDAY = raw.get("HIGHDAY").toString();
        String LOWDAY = raw.get("LOWDAY").toString();
        String MKTCAP = raw.get("MKTCAP").toString();
        String SUPPLY = raw.get("SUPPLY").toString();
        String TOTALVOLUME24H = raw.get("TOTALVOLUME24H").toString();
        String TOTALVOLUME24HTO = raw.get("TOTALVOLUME24HTO").toString();
        String CHANGEPCTDAY = raw.get("CHANGEPCTDAY").toString();
        String CHANGEPCT24HOUR = raw.get("CHANGEPCT24HOUR").toString();
        String CHANGE24HOUR = raw.get("CHANGE24HOUR").toString();
        String SYMBOL = display.get("FROMSYMBOL").toString();


        mCoinData[i][1] = PRICE;
        mCoinData[i][2] = LASTUPDATE;
        mCoinData[i][3] = OPENDAY;
        mCoinData[i][4] = HIGHDAY;
        mCoinData[i][5] = LOWDAY;
        mCoinData[i][6] = MKTCAP;
        mCoinData[i][7] = SUPPLY;
        mCoinData[i][8] = TOTALVOLUME24H;
        mCoinData[i][9] = TOTALVOLUME24HTO;
        mCoinData[i][10] = CHANGEPCTDAY;
        mCoinData[i][11] = CHANGEPCT24HOUR;
        mCoinData[i][12] = CHANGE24HOUR;
        mCoinData[i][13] = SYMBOL;

    }
    }catch(Exception e) {
    System.out.println(e);
    }
        return(mCoinData);
    }

    /**
     * Function used in {@link Home} to update the coin prices in real time
     *
     * @ms.Pre-condition called when update is requested
     * @ms.Post-condition Updates coin prices to real time
     *
     * */
    static String[][] updatePrices(){

        String inputLine;
        StringBuffer coinData;
        String coinPrices = null;
        JSONObject coins = null;

        try {

            String url = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH,XRP,BCH,XLM,EOS,LTC,USDT,ADA,BNB,XMR,TRX,IOT,DASH,NEO,ETC,ZRX,XEM,ZEC,VEN,DOGE,BTG,QTUM,AE,LINK,BAT,DCR,XRB,LSK,ICX,DGB,BTS,SNT,SC,QASH,XVG,WTC,GNO,WAVES,PPT,ETP,GNT,FUN,LRC,STORJ,KMD,STRAT,MCO,CVC,REP,PAY,ARDR,XUC,BNT,DGD,KNC,MAID,SALT,HSR,ARK,STEEM,PIVX,MONA,DCN,ZEN,SUB,VERI,NXT,SYS,GAS&tsyms="+currencyChosen+"&extraParams=cryptoSimulatorSchoolProject";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            coinData = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                coinData.append(inputLine);
            } in.close();
            coinPrices = coinData.toString();

        } catch(Exception e) {
            System.out.println(e);
        }

        try{
        coins = new JSONObject(coinPrices);

         for(int i = 0; i < 69; i++) {
                String temp;
               temp = coins.getJSONObject(mCoinData[i][0]).get(currencyChosen).toString();
                mCoinData[i][1]= temp;
            }
        } catch(Exception e) {
            System.out.println(e);
        }

        return(mCoinData);
    }

    /**
     * Function used in {@link Home} to update the coin prices in real time
     *
     * @ms.Pre-condition called when update is requested
     * @ms.Post-condition Updates coin prices to real time
     *
     * */
    static String[][] updateCoinData(){

        String coinPrices = null;
        try {
            String url = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=BTC,ETH,XRP,BCH,XLM,EOS,LTC,USDT,ADA,BNB,XMR,TRX,IOT,DASH,NEO,ETC,ZRX,XEM,ZEC,VEN,DOGE,BTG,QTUM,AE,LINK,BAT,DCR,XRB,LSK,ICX,DGB,BTS,SNT,SC,QASH,XVG,WTC,GNO,WAVES,PPT,ETP,GNT,FUN,LRC,STORJ,KMD,STRAT,MCO,CVC,REP,PAY,ARDR,XUC,BNT,DGD,KNC,MAID,SALT,HSR,ARK,STEEM,PIVX,MONA,DCN,ZEN,SUB,VERI,NXT,SYS,GAS&tsyms="+currencyChosen+"&extraParams=cryptoSimulatorSchoolProject";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer coinData;
            coinData = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                coinData.append(inputLine);
            } in.close();
            coinPrices = coinData.toString();
        } catch(Exception e) {
            System.out.println(e);
        }

        try{
         JSONObject coins = new JSONObject(coinPrices);

         for(int i = 0; i < 69; i++) {

            JSONObject raw = coins.getJSONObject("RAW").getJSONObject(mCoinData[i][0]).getJSONObject(currencyChosen);
            JSONObject display = coins.getJSONObject("DISPLAY").getJSONObject(mCoinData[i][0]).getJSONObject(currencyChosen);

            String PRICE = raw.get("PRICE").toString();
            String LASTUPDATE = raw.get("LASTUPDATE").toString();
            String OPENDAY = raw.get("OPENDAY").toString();
            String HIGHDAY = raw.get("HIGHDAY").toString();
            String LOWDAY = raw.get("LOWDAY").toString();
            String MKTCAP = raw.get("MKTCAP").toString();
            String SUPPLY = raw.get("SUPPLY").toString();
            String TOTALVOLUME24H = raw.get("TOTALVOLUME24H").toString();
            String TOTALVOLUME24HTO = raw.get("TOTALVOLUME24HTO").toString();
            String CHANGEPCTDAY = raw.get("CHANGEPCTDAY").toString();
            String CHANGEPCT24HOUR = raw.get("CHANGEPCT24HOUR").toString();
            String CHANGE24HOUR = raw.get("CHANGE24HOUR").toString();
            String SYMBOL = display.get("FROMSYMBOL").toString();

            double price = Double.parseDouble(PRICE);
            System.out.println(price);
            double changePctDay = Double.parseDouble(CHANGEPCTDAY);
            System.out.println(changePctDay);

            mCoinData[i][1]= PRICE;
            mCoinData[i][2]= LASTUPDATE;
            mCoinData[i][3]= OPENDAY;
            mCoinData[i][4]= HIGHDAY;
            mCoinData[i][5]= LOWDAY;
            mCoinData[i][6]= MKTCAP;
            mCoinData[i][7]= SUPPLY;
            mCoinData[i][8]= TOTALVOLUME24H;
            mCoinData[i][9]= TOTALVOLUME24HTO;
            mCoinData[i][10]= CHANGEPCTDAY;
            mCoinData[i][11]= CHANGEPCT24HOUR;
            mCoinData[i][12]= CHANGE24HOUR;
            mCoinData[i][13]= SYMBOL;
            }

        } catch(Exception e) {
            System.out.println(e);
        }

        return(mCoinData);
    }

    /**
     * This function is called in {@link Search} when a user wants to search for
     * a certain coins statistics
     *
     * @ms.Pre-condition Passes in
     * @ms.Post-condition UI has full access to a coins stats
     *
     * @param coin is a string used for searching
     * @return array filled with coin statistics
     **/
    static double[] priceTimeStamp(String coin){
        double[] priceHolder = new double[5];
        String historical = null;

        Instant instant = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            instant = Instant.now();
        }
        long currentTimeStamp = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            currentTimeStamp = instant.toEpochMilli();
        }

        long previousDay = currentTimeStamp - 86400;

        try {
            String url = "https://min-api.cryptocompare.com/data/pricehistorical?fsym="+coin+"&tsyms=USD&ts="+currentTimeStamp+"&extraParams=cryptoSimulatorSchoolProject";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer coinData;
            coinData = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                coinData.append(inputLine);
            } in.close();
            historical = coinData.toString();
        } catch(Exception e) {
            System.out.println(e);
        }

        try{
            JSONObject history = new JSONObject(historical);
            String usdPrice = history.getJSONObject(coin).get(currencyChosen).toString();

            double price = Double.parseDouble(usdPrice);
            priceHolder[0]=price;
        } catch(Exception e) {
            System.out.println(e);
        }

        return priceHolder;
    }

}

//TODO: Write a function that returns an array of coin names