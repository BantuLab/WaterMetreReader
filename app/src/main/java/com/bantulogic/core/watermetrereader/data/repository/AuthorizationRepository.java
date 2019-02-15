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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class AuthorizationRepository {
    private AuthorizationDAO mAuthorizationDAO;
    private AuthorizationWebAPI mAuthorizationWebAPI;

    private MutableLiveData<Authorization> mAuthorization = new MutableLiveData<>();
    private LiveData<Authorization> mAuth;

    public AuthorizationRepository(Application application, String username, String password){

        AppDatabase db = AppDatabase.getDatabase(application);

        this.mAuthorizationDAO = db.mAuthorizationDAO();
        this.mAuthorizationWebAPI = ServiceGenerator.createService(AuthorizationWebAPI.class, username, password);
    }
    public AuthorizationRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);

        this.mAuthorizationDAO = db.mAuthorizationDAO();
    }

    public MutableLiveData<Authorization> getLoggedInUser(){
        if (mAuthorization ==  null){
            LiveData<Authorization> auth = this.mAuthorizationDAO.getLoggedInUser();
            if(auth != null && !auth.getValue().isTokenExpired() ){
                mAuthorization.setValue(auth.getValue());
           }
        }
        return mAuthorization;
    }

    public LiveData<Resource<Authorization>> login(String username, String password){
        return new NetworkBoundResource<Authorization, Token>(AppExecutors.getInstance()) {
            @Override
            protected void saveCallResult(@NonNull Token item) {
                //Create Auth Object from response
                try{
                    String token = item.getToken();
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
                    long result = mAuthorizationDAO.insertAuth(auth);
                    if (result == 1){
                        Log.i("AuthRepo:", "Auth token saved successuflly. Code: "+ String.valueOf(result));
                    } else {
                        Log.i("AuthRepo:", "Error saving Auth token to database. Code: "+ String.valueOf(result));
                    }
                    //Set App Logged-In User Object
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
                //Invalidate Auth if expired
                Authorization authObj;
                if (mAuth == null){
                    mAuth = mAuthorizationDAO.login(username, password);
                    authObj = mAuth.getValue();
                    if (authObj != null){
                        if (authObj.isTokenExpired()){
                            mAuthorizationDAO.invalidateAuth(authObj);
                            //Dispatch token expiry error here so the ViewModel can be prompted call
                            // login again and fetch it from the network
                            //In the meantime, just return null
                            return null;
                        }
                        else {
                            return mAuth;
                        }
                    }

                }
                return mAuth;
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

}
