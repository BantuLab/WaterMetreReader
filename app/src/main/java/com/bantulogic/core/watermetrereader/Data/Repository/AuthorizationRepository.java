package com.bantulogic.core.watermetrereader.Data.Repository;

import android.app.Application;
import android.util.Log;

import com.auth0.android.jwt.DecodeException;
import com.auth0.android.jwt.JWT;
import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.AppDatabase;
import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.DAO.AuthorizationDAO;
import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.Authorization;
import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.Token;
import com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI.AuthorizationWebAPI;
import com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI.Helpers.ApiResponse;
import com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI.Helpers.NetworkBoundResource;
import com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI.Helpers.ServiceGenerator;
import com.bantulogic.core.watermetrereader.Helpers.AppExecutors;
import com.bantulogic.core.watermetrereader.Helpers.MetreReaderApp;
import com.bantulogic.core.watermetrereader.Helpers.Resource;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Response;

public class AuthorizationRepository {
    private AuthorizationDAO mAuthorizationDAO;
    private AuthorizationWebAPI mAuthorizationWebAPI;

    private MutableLiveData<Authorization> mAuthorization;

    public AuthorizationRepository(Application application, String username, String password){

        AppDatabase db = AppDatabase.getDatabse(application);

        this.mAuthorizationDAO = db.mAuthorizationDAO();
        this.mAuthorizationWebAPI = ServiceGenerator.createService(AuthorizationWebAPI.class, username, password);
    }
    public AuthorizationRepository(Application application){
        AppDatabase db = AppDatabase.getDatabse(application);

        this.mAuthorizationDAO = db.mAuthorizationDAO();
    }

    public LiveData<Authorization> getLoggedInUser(){
        return mAuthorization;
    }

    public LiveData<Resource<Authorization>> login(String username, String password) {
        return new NetworkBoundResource<Authorization, Response>(AppExecutors.getInstance()) {
            @Override
            protected void saveCallResult(@NonNull Response item) {
                //Create Auth Object from response
                try{
                    Token newToken = (Token)item.body();
                    String token = newToken.getToken();
                    boolean loggedIn = true;
                    boolean loggedOut = false;
                    //Decode jwt token
                    JWT jwt = new JWT(token);
                    //Get token claims
                    String sub = jwt.getSubject();
                    String userType = jwt.getClaim("user_type").asString();
                    List<String> scope = jwt.getClaim("scope").asList(String.class);
                    Date iat = jwt.getIssuedAt();
                    Date exp = jwt.getExpiresAt();
                    List<String> aud = jwt.getAudience();
                    String iss = jwt.getIssuer();
                    //Create auth from token response
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
                            loggedOut,
                            loggedIn
                    );
                    //Save
                    mAuthorization.postValue(auth);
                    MetreReaderApp.setLoggedInUserAuthorization(mAuthorization);
                    mAuthorizationDAO.insertAuth(auth);
                    //Set App Logged-In User Object

                }catch (DecodeException e){
                    Log.e("Chaiwa", "Error during decoding jwt token: " + e.getMessage());
                }

            }

            @Override
            protected boolean shouldFetch(@Nullable Authorization data) {
                //Always fetch . Apply rate limiter later(Rate Limiter can be based on whether
                //the token has expired
                return true;
            }

            @NonNull
            @Override
            protected LiveData<Authorization> loadFromDb() {
                //Invalidate Auth if expired
                Authorization authObj;
                LiveData<Authorization> auth = mAuthorizationDAO.login(username, password);
                if (auth != null){
                    authObj = auth.getValue();
                    if (authObj.isTokenExpired()){
                        mAuthorizationDAO.invalidateAuth(authObj);
                        //Dispatch token expiry error here so the ViewModel can be prompted call
                        // login again and fetch it from the network
                        //In the meantime, just return null
                        return null;
                    }
                    else {
                        return auth;
                    }
                }
                return null;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Response>> createCall() {
                return mAuthorizationWebAPI.login(username, password);
            }

            @Override
            protected void onFetchFailed() {
                //maybe reset RateLimiter to allow App to ask for login  again?

            }
        }.getAsLiveData();
    }
}
