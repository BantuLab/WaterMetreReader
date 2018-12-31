package com.bantulogic.core.watermetrereader.Helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class ConnectivityBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
    String extra = intent.getExtras().toString();
        Log.d("ConnectivityReceiver",extra);
    }
}
