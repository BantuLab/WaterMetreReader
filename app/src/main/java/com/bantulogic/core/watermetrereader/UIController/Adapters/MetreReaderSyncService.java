package com.bantulogic.core.watermetrereader.UIController.Adapters;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Define a Service that returns an <code><a href="/reference/android/os/IBinder.html">IBinder</a></code>
 * for the sync adapter class, allowing the sync adapter framework to call
 * onPerformSync()
 */
public class MetreReaderSyncService extends Service {
    //Storage for an instance of the sync adapter
    private static MetreReaderSyncAdapter sMetreReaderSyncAdapter = null;
    //Object to use as a thread-safe lock
    private static final Object sMetreReaderSyncAdapterLock = new Object();
    /*
     * Instantiate the sync adapter object.
     */
    @Override
    public void onCreate() {
        /*
         * Create the sync adapter as a singleton.
         * Set the sync adapter as syncable
         * Disallow parallel syncs
         */
        //super.onCreate();
        synchronized (sMetreReaderSyncAdapterLock) {
            if (sMetreReaderSyncAdapter == null) {
                sMetreReaderSyncAdapter = new MetreReaderSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    /**
     * Return an object that allows the system to invoke
     * the sync adapter
     * @param intent
     * @return
     */
    @Override
    public IBinder onBind(Intent intent) {
        /*
         * Get the object that allows external processes
         * to call onPerformSync(). The object is created
         * in the base class code when the SyncAdapter
         * constructors call super
         */
        return sMetreReaderSyncAdapter.getSyncAdapterBinder();
    }


}
