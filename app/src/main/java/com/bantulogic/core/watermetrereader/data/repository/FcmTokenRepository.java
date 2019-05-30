package com.bantulogic.core.watermetrereader.data.repository;

import android.app.Application;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.AppDatabase;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao.FcmTokenDAO;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Authorization;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.FcmToken;
import com.bantulogic.core.watermetrereader.data.datasource.network.FcmTokenWebAPI;
import com.bantulogic.core.watermetrereader.data.datasource.network.helpers.ApiResponse;
import com.bantulogic.core.watermetrereader.data.datasource.network.helpers.NetworkBoundResource;
import com.bantulogic.core.watermetrereader.helpers.AppExecutors;
import com.bantulogic.core.watermetrereader.helpers.Resource;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

public class FcmTokenRepository {
    private FcmTokenWebAPI mFcmTokenWebAPI;
    private FcmTokenDAO mFcmTokenDAO;
    private LiveData<FcmToken> mFcmToken;
    private LiveData<Authorization> mAuthorization;

    public FcmTokenRepository(Application application){
        AppDatabase db = AppDatabase.getDatabase(application);
        this.mFcmTokenDAO = db.fcmTokenDAO();
        mAuthorization = db.mAuthorizationDAO().getLoggedInUser();
    }

    public LiveData<Resource<FcmToken>> registerFcmToken(){
        return new NetworkBoundResource<FcmToken, FcmToken>(AppExecutors.getInstance()) {
            @Override
            protected void saveCallResult(@NonNull FcmToken item) {
                mFcmTokenDAO.insertFcmToken(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable FcmToken data) {
                //If registerFcmToken is called then always send token to server
                //Revise this logic
                return true;
            }

            @NonNull
            @Override
            protected LiveData<FcmToken> loadFromDb() {
                return mFcmToken;
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<FcmToken>> createCall() {
                return mFcmTokenWebAPI.register_fcm_token();
            }

            @Override
            protected void onFetchFailed() {
                //maybe reset RateLimiter to allow App to ask for saving token again?

            }
        }.getAsLiveData();
    }
    public void saveFcmToken(FcmToken fcmToken){
        this.mFcmTokenDAO.insertFcmToken(fcmToken);
    }
}
