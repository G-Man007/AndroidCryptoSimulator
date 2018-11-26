package com.example.branden.cryptocurrencytradingsimulator;
//imports needed to connect to API

import java.io.BufferedReader;
import java.io.IOException;
import java.time.Instant;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class javaCryptoCompAPI {


    public static String currencyChosen = "USD";
    private static String[][] mCoinData = new String[69][14];
    private static String[] mCoinShort = {
            "BTC", "ETH", "XRP", "BCH", "XLM", "EOS", "LTC", "USDT", "ADA", "XMR",
            "TRX", "IOT", "DASH", "NEO", "ETC", "ZRX", "XEM", "ZEC", "VEN", "DOGE",
            "BTG", "QTUM", "AE", "LINK", "BAT", "DCR", "XRB", "LSK", "ICX", "DGB",
            "BTS", "SNT", "SC", "QASH", "XVG", "WTC", "GNO", "WAVES", "PPT", "ETP",
            "GNT", "FUN", "LRC", "STORJ", "KMD", "STRAT", "MCO", "CVC", "REP", "PAY",
            "ARDR", "XUC", "BNT", "DGD", "KNC", "MAID", "SALT", "HSR", "ARK", "STEEM",
            "PIVX", "MONA", "DCN", "ZEN", "SUB", "VERI", "NXT", "SYS", "GAS"
    };
    private static String[] mCoinNames = {"Bitcoin", "Ethereum", "Ripple", "Bitcoin Cash", "Stellar", "EOS", "Litecoin", "Tether", "Cardano", "Monero", "Tronix", "IOTA", "DigitalCash", "NEO", "Ethereum Classic", "0x", "NEM", "ZCash", "VeChain", "Dogecoin", "OmisGo", "Bitcoin Gold", "QTUM",
            "Aeternity", "ChainLink", "Basic Attention Token", "Decred", "Nano", "Lisk", "ICON Project", "DigiByte", "Bitshares", "Status Network Token", "Saicoin", "Quoine Liquid", "Verge", "Waltonchain", "Gnosis", "Waves", "Populous", "Metaverse", "Golem Netwok Token", "FunFair", "Loopring", "Stori", "Komodo", "Stratis", "Civic", "Augur", "TenX",
            "Ardor", "Exchange Union", "Bancor Network Token", "Digix DAO", "Kyber Network", "MaidSafe Coin", "Salt Lending", "Hshare", "ARK", "Steem", "Private Instant Verified Transaction", "MonaCoin", "Dentacoin", "ZenCash", "Substratum Network", "Veritaseum", "Nxt", "SysCoin", "Gas"
    };

    /**
     * This function initializes all of the coin prices {@link Home} {@link Trade} {@link Search} will use all of this pricing info
     * when the app launches, also called when @see #update()
     *
     * @param currency to initialize what world currency will be used, USD is set default
     * @return mCoinData with an initialized list of values
     * @ccs.Pre-condition Function can be called at any time updated prices are wanted for all of the coins
     * @ccs.Post-condition Will initialize a base data structure to pull from
     * @see #mCoinData
     **/
    static String[][] initializeCoinData(String currency) {
        currencyChosen = currency;
        String coinPrices = null;
        try {
            String url = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=BTC,ETH,XRP,BCH,XLM,EOS,LTC,USDT,ADA,BNB,XMR,TRX,IOT,DASH,NEO,ETC,ZRX,XEM,ZEC,VEN,DOGE,BTG,QTUM,AE,LINK,BAT,DCR,XRB,LSK,ICX,DGB,BTS,SNT,SC,QASH,XVG,WTC,GNO,WAVES,PPT,ETP,GNT,FUN,LRC,STORJ,KMD,STRAT,MCO,CVC,REP,PAY,ARDR,XUC,BNT,DGD,KNC,MAID,SALT,HSR,ARK,STEEM,PIVX,MONA,DCN,ZEN,SUB,VERI,NXT,SYS,GAS&tsyms=" + currency + "&extraParams=cryptoSimulatorSchoolProject";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer coinData;
            coinData = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                coinData.append(inputLine);
            }
            in.close();
            con.disconnect();
            coinPrices = coinData.toString();

        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            JSONObject coins = new JSONObject(coinPrices);

            for (int i = 0; i < 69; i++) {

                JSONObject raw = coins.getJSONObject("DISPLAY").getJSONObject(mCoinShort[i]).getJSONObject(currencyChosen);

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

                mCoinData[i][0] = mCoinShort[i];
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
                mCoinData[i][13] = mCoinNames[i];

            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return (mCoinData);
    }

    /**
     * Function used in {@link Home} to update the coin prices in real time
     *
     * @return mCoinData with updated pricing
     * @ccs.Pre-condition called when update is requested
     * @ccs.Post-condition Updates coin prices to real time
     */
    static String[][] updatePrices() {

        String inputLine;
        StringBuffer coinData;
        String coinPrices = null;
        JSONObject coins = null;

        try {

            String url = "https://min-api.cryptocompare.com/data/pricemulti?fsyms=BTC,ETH,XRP,BCH,XLM,EOS,LTC,USDT,ADA,BNB,XMR,TRX,IOT,DASH,NEO,ETC,ZRX,XEM,ZEC,VEN,DOGE,BTG,QTUM,AE,LINK,BAT,DCR,XRB,LSK,ICX,DGB,BTS,SNT,SC,QASH,XVG,WTC,GNO,WAVES,PPT,ETP,GNT,FUN,LRC,STORJ,KMD,STRAT,MCO,CVC,REP,PAY,ARDR,XUC,BNT,DGD,KNC,MAID,SALT,HSR,ARK,STEEM,PIVX,MONA,DCN,ZEN,SUB,VERI,NXT,SYS,GAS&tsyms=" + currencyChosen + "&extraParams=cryptoSimulatorSchoolProject";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            coinData = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                coinData.append(inputLine);
            }
            in.close();
            con.disconnect();
            coinPrices = coinData.toString();

        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            coins = new JSONObject(coinPrices);

            for (int i = 0; i < 69; i++) {
                String temp;
                temp = coins.getJSONObject(mCoinShort[i]).get(currencyChosen).toString();
                mCoinData[i][1] = temp;
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return (mCoinData);
    }

    /**
     * Function used in {@link Home} to update the coin prices in real time
     *
     * @ccs.Pre-condition called when update is requested
     * @ccs.Post-condition Updates coin prices to real time
     */
    static void updateCoinData() {

        String coinPrices = null;
        try {
            String url = "https://min-api.cryptocompare.com/data/pricemultifull?fsyms=BTC,ETH,XRP,BCH,XLM,EOS,LTC,USDT,ADA,BNB,XMR,TRX,IOT,DASH,NEO,ETC,ZRX,XEM,ZEC,VEN,DOGE,BTG,QTUM,AE,LINK,BAT,DCR,XRB,LSK,ICX,DGB,BTS,SNT,SC,QASH,XVG,WTC,GNO,WAVES,PPT,ETP,GNT,FUN,LRC,STORJ,KMD,STRAT,MCO,CVC,REP,PAY,ARDR,XUC,BNT,DGD,KNC,MAID,SALT,HSR,ARK,STEEM,PIVX,MONA,DCN,ZEN,SUB,VERI,NXT,SYS,GAS&tsyms=" + currencyChosen + "&extraParams=cryptoSimulatorSchoolProject";
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer coinData;
            coinData = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                coinData.append(inputLine);
            }
            in.close();
            coinPrices = coinData.toString();
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            JSONObject coins = new JSONObject(coinPrices);

            for (int i = 0; i < 69; i++) {

                JSONObject raw = coins.getJSONObject("RAW").getJSONObject(mCoinShort[i]).getJSONObject(currencyChosen);

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

                double price = Double.parseDouble(PRICE);
                System.out.println(price);
                double changePctDay = Double.parseDouble(CHANGEPCTDAY);
                System.out.println(changePctDay);

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

            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * This function is called in {@link Search} when a user wants to search for
     * a certain coins statistics
     *
     * @param coin is a string that is the name of the coin whose data is sought.
     * @return array with past 7 days of pricing information.
     * @ccs.Pre-condition A trade activity is started.
     * @ccs.Post-condition No post condition.
     **/
    static double[] weeklyPriceInfo(String coin) {
        String urlCoin = nameConversion(coin);
        double[] priceHolder = new double[7];
        String usdPrice = null;
        double price = 0;
        Instant instant = Instant.now();
        long currentTimeStamp = instant.toEpochMilli();
        String time = Long.toString(currentTimeStamp);
        String previous = time.substring(0, time.length() - 3);
        long previousDay = Long.parseLong(previous);

        for (int i = 0; i < 7; i++) {
            try {
                String url = "https://min-api.cryptocompare.com/data/pricehistorical?fsym=" + urlCoin + "&tsyms=" + currencyChosen + "&ts=" + previousDay + "&extraParams=cryptoSimulatorSchoolProject";
                JSONObject history = getJSONObjectFromURL(url);
                usdPrice = history.getJSONObject(urlCoin).get(currencyChosen).toString();
                price = Double.parseDouble(usdPrice);

                priceHolder[i] = price;
                System.out.println(priceHolder[i]);
                previousDay = previousDay - 86400;
            } catch(IOException e) {
                e.printStackTrace();
            } catch(JSONException e){
                e.printStackTrace();
            }
        }
        return priceHolder;
    }

    /**
     * Function used in {@link Search} to populate a list of used coins
     *
     * @return mCoinNames array filled with top crypto names
     * @ccs.Pre-condition invoked to populate array of used crypto currencies
     * @ccs.Post-condition Returns an array of coin names
     */
    static String[] getCoinNames() {
        return mCoinNames;
    }

    /**
     * Function used in {@link Search} to populate a list coins data
     *
     * @param coin string to search for
     * @return returns data of desired coin
     * @ccs.Pre-condition invoked to populate array of used crypto currencies
     * @ccs.Post-condition Returns an array of coin names
     */
    static String[] search(String coin) {
        String coinInfo[] = new String[14];
        for (int i = 0; i < 69; i++) {
            if (coin.equals(mCoinData[i][13]) || coin.equals(mCoinData[i][0])) {
                System.arraycopy(mCoinData[i], 0, coinInfo, 0, 14);
                break;
            }
        }
        return coinInfo;
    }

    /**
     * Function used to convert from full coin names to coin abbreviations to be used in the API URL
     *
     * @param coin string to search for
     * @return returns a string containing the abbreviation of the passed in coin
     * @ccs.Pre-condition A coins information is being accessed through the API
     * @ccs.Post-condition No post condition
     */
    static String nameConversion(String coin) {
        for (int i = 0; i < 69; i++) {
            if (mCoinNames[i].equals(coin)) {
                return(mCoinShort[i]);
            }
        }
        return("ERROR");
    }

    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */);
        urlConnection.setConnectTimeout(15000 /* milliseconds */);
        urlConnection.setDoOutput(true);
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();
        System.out.println("JSON: " + jsonString);
        urlConnection.disconnect();

        return new JSONObject(jsonString);
    }
}
