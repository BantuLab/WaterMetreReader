package com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI.Helpers;

import android.os.SystemClock;
import android.util.ArrayMap;

import java.util.concurrent.TimeUnit;

/**
 * Utility class that declares whether we should fetch some data or not
 */
public class RateLimiter <KEY>{
    private ArrayMap<KEY, Long> mTimestamps = new ArrayMap<>();
    private Long mTimeout;

    public RateLimiter(int timeout, TimeUnit timeUnit) {
        this.mTimeout = timeUnit.toMillis(timeout);
    }

    public synchronized boolean canFetch(KEY key){
        Long lastFetched =  mTimestamps.get(key);
        Long now = now();

        if (lastFetched == null) {
            mTimestamps.put(key, now);
            return true;
        }
        if ((now - lastFetched) > mTimeout) {
            mTimestamps.put(key, now);
            return true;
        }
        return false;
    }

    private Long now (){
        return SystemClock.uptimeMillis();
    }

    public synchronized void reset(KEY key){
        mTimestamps.remove(key);
    }
}
