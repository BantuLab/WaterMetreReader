package com.bantulogic.core.watermetrereader.data.repository;

import android.app.Application;
import android.util.Log;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.AppDatabase;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao.MetreAccountDAO;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Authorization;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.MetreAccount;
import com.bantulogic.core.watermetrereader.data.datasource.network.MetreAccountWebAPI;
import com.bantulogic.core.watermetrereader.data.datasource.network.helpers.ServiceGenerator;
import com.bantulogic.core.watermetrereader.helpers.MetreReaderApp;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MetreAccountRepository {
    //Local Database API Member Variables
    private MetreAccountDAO mMetreAccountDAO;
    private LiveData<List<MetreAccount>> mAllMetreAccounts;
    private LiveData<MetreAccount> mMetreAccount;

    //Web/Remote/Network API Member Variables
    private MetreAccountWebAPI mMetreAccountWebAPI;
    private Call<List<MetreAccount>> mGetMetreAccountsByUser;
    private MutableLiveData<List<MetreAccount>> mMetreAccountsFromWebAPI = new MutableLiveData<>();

    //Application Global State Variables
    private LiveData<Authorization> mAuthorization = MetreReaderApp.getLoggedInUserAuthorization();

    //Constructor
    public MetreAccountRepository(Application application){
        //Local API Initialization
        AppDatabase db = AppDatabase.getDatabse(application);

        mMetreAccountDAO = db.metreAccountDAO();
        mAllMetreAccounts = mMetreAccountDAO.getAllMetreAccountsByUser(mAuthorization.getValue().getSub());

        //Web API Initialization
        mMetreAccountWebAPI = ServiceGenerator.createService(MetreAccountWebAPI.class, mAuthorization.getValue().getToken());
        mGetMetreAccountsByUser = mMetreAccountWebAPI.getMetreAccountsByUser(mAuthorization.getValue().getSub());
    }

    //region METRE ACCOUNT API IMPLEMENTATION
    public MutableLiveData<List<MetreAccount>> getMetreAccountsFromWebAPI(){
        //Remember to get clever here and refactor this method
        mGetMetreAccountsByUser.enqueue(new Callback<List<MetreAccount>>() {
            @Override
            public void onResponse(Call<List<MetreAccount>> call, Response<List<MetreAccount>> response) {
                if (response.isSuccessful()){
                    //Mutate the list of Metre Accounts
                    mMetreAccountsFromWebAPI.postValue(response.body());
                    Log.d("Chaiwa","Call to /api/metre_accounts?assigned_user_id=\"Some User Id\" returned some data: " +  response.body());
                }
                else {
                    Log.d("Chaiwa","Call to /api/metre_accounts?assigned_user_id=\"Some User Id\" failed with error: " + response.errorBody());
                    mMetreAccountsFromWebAPI.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<MetreAccount>> call, Throwable t) {
                t.printStackTrace();

            }
        });

        return mMetreAccountsFromWebAPI;
    }
    //endregion
}
