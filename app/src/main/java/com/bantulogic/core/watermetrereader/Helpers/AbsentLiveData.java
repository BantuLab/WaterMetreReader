package com.bantulogic.core.watermetrereader.Helpers;

import androidx.lifecycle.LiveData;

/**
 * A LiveData class that has {@code null} value
 */
@SuppressWarnings("unchecked")
public class AbsentLiveData extends LiveData {
    private AbsentLiveData(){
        postValue(null);
    }

    public static <T> LiveData<T> create(){
        return new AbsentLiveData();
    }
}
