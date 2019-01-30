package com.bantulogic.core.watermetrereader.ViewModel;

import android.app.Application;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.Authorization;
import com.bantulogic.core.watermetrereader.Data.Repository.AuthorizationRepository;
import com.bantulogic.core.watermetrereader.Helpers.Resource;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class AuthorizationViewModel extends AndroidViewModel {
    private AuthorizationRepository mAuthorizationRepository;
    private LiveData<Resource<Authorization>> mAuthorization;
    private final Application mApplication;
    public AuthorizationViewModel(@NonNull Application application) {
        super(application);
        this.mApplication = application;
        this.mAuthorizationRepository = null;
    }

    public LiveData<Resource<Authorization>> login(String username, String password){

        mAuthorizationRepository = new AuthorizationRepository(this.mApplication,username,password);

        mAuthorization = mAuthorizationRepository.login(username, password);

        return mAuthorization;
    }
}
