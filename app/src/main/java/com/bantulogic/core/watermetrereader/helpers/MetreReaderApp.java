package com.bantulogic.core.watermetrereader.helpers;

import android.app.Application;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Authorization;
import com.bantulogic.core.watermetrereader.data.repository.AuthorizationRepository;
import com.google.firebase.FirebaseApp;

import androidx.lifecycle.LiveData;

public class MetreReaderApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(getApplicationContext());
    }
}
