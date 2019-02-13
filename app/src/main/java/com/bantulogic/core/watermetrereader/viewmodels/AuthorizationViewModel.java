package com.bantulogic.core.watermetrereader.viewmodels;

import android.app.Application;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Authorization;
import com.bantulogic.core.watermetrereader.data.repository.AuthorizationRepository;
import com.bantulogic.core.watermetrereader.helpers.Resource;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AuthorizationViewModel extends AndroidViewModel {
    private AuthorizationRepository mAuthorizationRepository;
    private LiveData<Resource<Authorization>> mAuthorization;
    public MutableLiveData<Authorization> loggedInUserAuth;
    private final Application mApplication;

    //Public ViewModel Properties for Data Binding



    public AuthorizationViewModel(@NonNull Application application) {
        super(application);
        this.mApplication = application;
        this.mAuthorizationRepository = new AuthorizationRepository(application);
    }

    public LiveData<Resource<Authorization>> login(String username, String password){

        mAuthorizationRepository = new AuthorizationRepository(this.mApplication,username,password);

        mAuthorization = mAuthorizationRepository.login(username, password);

        return mAuthorization;
    }

    public LiveData<Authorization> getLoggedInUserAuth() {
        if (loggedInUserAuth == null){
            loggedInUserAuth = new MutableLiveData<>();
            loggedInUserAuth = mAuthorizationRepository.getLoggedInUser();


        }
        return loggedInUserAuth;
    }

    public void setLoggedInUserAuth(Authorization loggedInUserAuth) {
        this.loggedInUserAuth.postValue(loggedInUserAuth);
    }
}
