package com.bantulogic.core.watermetrereader.Helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class InternetConnectivityStatus {
    private static Context context;
    private static InternetConnectivityStatus instance = new InternetConnectivityStatus();
    private ConnectivityManager connectivityManager;
    private NetworkInfo wifiInfo, mobileInfo;
    private boolean connected = false;

    public static InternetConnectivityStatus getInstance(Context ctx){
        context = ctx.getApplicationContext();
        return instance;
    }

    public boolean isOnline(){
        try {
            connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo =  connectivityManager.getActiveNetworkInfo();
            connected = networkInfo != null && networkInfo.isConnected();
            return connected;
        } catch (Exception e){
            System.out.println("CheckConnectivity Exception: " + e.getMessage());
            Log.v("connectivity", e.toString());
        }
        return connected;
    }

}
