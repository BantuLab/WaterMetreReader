package com.bantulogic.core.watermetrereader.Data.Repository;

import android.app.Application;
import android.util.Log;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.AppDatabase;
import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.DAO.MetreAccountDAO;
import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.MetreAccount;
import com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI.MetreAccountWebAPI;
import com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI.Helpers.ServiceGenerator;
import com.bantulogic.core.watermetrereader.Helpers.Authorization;
import com.bantulogic.core.watermetrereader.Helpers.MetreReaderApp;

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
    private Authorization mAuthorization = MetreReaderApp.getLoggedInUserAuthorization();

    //Constructor
    public MetreAccountRepository(Application application){
        //Local API Initialization
        AppDatabase db = AppDatabase.getDatabse(application);

        mMetreAccountDAO = db.metreAccountDAO();
        mAllMetreAccounts = mMetreAccountDAO.getAllMetreAccountsByUser(mAuthorization.getUserId());

        //Web API Initialization
        mMetreAccountWebAPI = ServiceGenerator.createService(MetreAccountWebAPI.class, mAuthorization.getToken());
        mGetMetreAccountsByUser = mMetreAccountWebAPI.getMetreAccountsByUser(mAuthorization.getUserId());
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
