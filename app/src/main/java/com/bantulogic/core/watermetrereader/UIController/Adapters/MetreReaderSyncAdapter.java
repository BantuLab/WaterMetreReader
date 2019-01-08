package com.bantulogic.core.watermetrereader.UIController.Adapters;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

import com.bantulogic.core.watermetrereader.Helpers.MetreReaderApp;

/**
 * Handle the transfer of data between a server and an
 * app, using the Android sync adapter framework
 * The sync adapter component does not automatically do data transfer.
 * Instead, it encapsulates your data transfer code, so that the sync adapter framework can run
 * the data transfer in the background, without involvement from your app. When the framework is
 * ready to sync your application's data, it invokes your implementation of the
 * method onPerformSync()
 */
public class MetreReaderSyncAdapter extends AbstractThreadedSyncAdapter {
    //Global variables
    //Define a variable to contain a content resolver instance
    ContentResolver mContentResolver;

    /**
     * Setup the sync adapter
     * @param context
     * @param autoInitialize
     */
     public MetreReaderSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
         /**
          * If your app uses a content resolver, get an instance of it
          * from the incoming Context
          */
         mContentResolver = context.getContentResolver();
    }

    /**
     * Setup the sync adapter. This form of the
     * constructor maintains compatibility with Android 3.0
     * and later platform versions
     * @param context
     * @param autoInitialize
     * @param allowParallelSyncs
     */
    public MetreReaderSyncAdapter(
            Context context, boolean autoInitialize, boolean allowParallelSyncs) {
        super(context, autoInitialize, allowParallelSyncs);
        /**
         * If your app uses a content resolver, get an instance of it
         * from thee incoming Context
         */
        mContentResolver = context.getContentResolver();
    }

    /*
     * Specify the code you want to run in the sync adapter. The entire
     * sync adapter runs in a background thread, so you don't have to set
     * up your own background processing
     */
    @Override
    public void onPerformSync(
            Account account,
            Bundle bundle,
            String s,
            ContentProviderClient contentProviderClient,
            SyncResult syncResult) {
        /*
         * Put the data transfer code here.
         */

    }
}
