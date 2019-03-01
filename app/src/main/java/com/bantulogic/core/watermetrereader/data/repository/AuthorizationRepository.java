package com.bantulogic.core.watermetrereader.data.repository;

import android.app.Application;
import android.util.Log;

import com.auth0.android.jwt.JWT;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.AppDatabase;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao.AuthorizationDAO;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Authorization;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Token;
import com.bantulogic.core.watermetrereader.data.datasource.network.AuthorizationWebAPI;
import com.bantulogic.core.watermetrereader.data.datasource.network.helpers.ApiResponse;
import com.bantulogic.core.watermetrereader.data.datasource.network.helpers.NetworkBoundResource;
import com.bantulogic.core.watermetrereader.data.datasource.network.helpers.ServiceGenerator;
import com.bantulogic.core.watermetrereader.helpers.AppExecutors;
import com.bantulogic.core.watermetrereader.helpers.Resource;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

public class AuthorizationRepository {
    private AuthorizationDAO mAuthorizationDAO;
    private AuthorizationWebAPI mAuthorizationWebAPI;
    private LiveData<Authorization> mAuthorization;

    public AuthorizationRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        this.mAuthorizationDAO = db.mAuthorizationDAO();
        this.mAuthorization = mAuthorizationDAO.getLoggedInUser();
    }
    public LiveData<Authorization> getAuthorizationResult() {
        return mAuthorization;
    }
    public LiveData<Resource<Authorization>> login(String username, String password){
        this.mAuthorizationWebAPI = ServiceGenerator.createService(AuthorizationWebAPI.class, username, password);
        return new NetworkBoundResource<Authorization, Token>(AppExecutors.getInstance()) {
            @Override
            protected void saveCallResult(@NonNull Token item) {
                try{
                    String token = item.getToken();
                    boolean loggedIn = true;
                    boolean loggedOut = false;

                    JWT jwt = new JWT(token);

                    String sub = jwt.getSubject();
                    String userType = jwt.getClaim("user_type").asString();
                    List<String> scope = jwt.getClaim("scope").asList(String.class);
                    Date iat = jwt.getIssuedAt();
                    Date exp = jwt.getExpiresAt();
                    List<String> aud = jwt.getAudience();
                    String iss = jwt.getIssuer();

                    Authorization auth = new Authorization(
                            username,
                            password,
                            token,
                            sub,
                            userType,
                            scope,
                            iat,
                            exp,
                            aud,
                            iss,
                            loggedIn
                    );
                    mAuthorizationDAO.insertAuth(auth);
                }catch (Exception e){
                    Log.e("Chaiwa", "Error during decoding or saving jwt token: " + e.getMessage());
                }
            }

            @Override
            protected boolean shouldFetch(@Nullable Authorization data) {
                //Apply logic to check when to fetch, set RateLimiter
                return true;
            }

            @NonNull
            @Override
            protected LiveData<Authorization> loadFromDb() {
                return mAuthorization;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Token>> createCall() {
                return mAuthorizationWebAPI.login(username, password);
            }

            @Override
            protected void onFetchFailed() {
                //maybe reset RateLimiter to allow App to ask for login  again?
            }
        }.getAsLiveData();
    }

    public void logoutCurrentUser(){
        if (this.mAuthorization != null){
            AppExecutors.getInstance().getDiskIO().execute(()->{
                Authorization mAuthorizationObj = this.mAuthorization.getValue();
                mAuthorizationObj.setLoggedIn(false);
                mAuthorizationDAO.logoutCurrentUser(mAuthorizationObj);
            });
        }
    }
}