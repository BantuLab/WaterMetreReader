package com.bantulogic.core.watermetrereader.viewmodels;

import android.app.Application;
import android.util.Log;

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

    public AuthorizationViewModel(@NonNull Application application) {
        super(application);
        this.mAuthorizationRepository = new AuthorizationRepository(application);
    }

    public LiveData<Resource<Authorization>> login(String username, String password){
        this.mAuthorization = this.mAuthorizationRepository.login(username, password);
        return this.mAuthorization;
    }
    public LiveData<Authorization> getLoggedInUserAuth() {
        return mAuthorizationRepository.getAuthorizationResult();
    }
    public void logoutCurrentUser(){
        this.mAuthorizationRepository.logoutCurrentUser();
    }
    public void loginCurrentUser(Authorization authorization){
        this.mAuthorizationRepository.loginCurrentUser(authorization);
    }
}