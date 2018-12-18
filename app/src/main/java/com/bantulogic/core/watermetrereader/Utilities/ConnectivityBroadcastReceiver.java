package com.bantulogic.core.watermetrereader.Utilities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ConnectivityBroadcastReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
    String extra = intent.getExtras().toString();
        Log.d("ConnectivityReceiver",extra);
    }
}
