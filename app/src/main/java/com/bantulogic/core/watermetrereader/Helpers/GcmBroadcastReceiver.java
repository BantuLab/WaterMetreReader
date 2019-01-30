package com.bantulogic.core.watermetrereader.Helpers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * If your app transfers data from a server and the server data changes frequently, you can use
 * a sync adapter to do downloads in response to data changes. To run the sync adapter, have the
 * server send a special message to a BroadcastReceiver in your app. In response to this message,
 * call ContentResolver.requestSync() to signal the sync adapter framework to run your sync adapter.
 *
 * Google Cloud Messaging (GCM) provides both the server and device components you need to make
 * this messaging system work. Using GCM to trigger transfers is more reliable and more efficient
 * than polling servers for status. While polling requires a Service that is always active,
 * GCM uses a BroadcastReceiver that's activated when a message arrives.
 * While polling at regular intervals uses battery power even if no updates are available,
 * GCM only sends messages when needed.
 */

public class GcmBroadcastReceiver extends BroadcastReceiver {
    // Constants
    // Content provider authority
    public static final String AUTHORITY = "com.example.android.datasync.provider";
    // Account type
    public static final String ACCOUNT_TYPE = "com.example.android.datasync";
    // Account
    public static final String ACCOUNT = "default_account";
    // Incoming Intent key for extended data
    public static final String KEY_SYNC_REQUEST =
            "com.example.android.datasync.KEY_SYNC_REQUEST";
    @Override
    public void onReceive(Context context, Intent intent) {
        //Get a GCM object instance
//        GoogleCloudMessaging mGcm = GoogleCloudMessaging.getInstance(context);

    }
}
