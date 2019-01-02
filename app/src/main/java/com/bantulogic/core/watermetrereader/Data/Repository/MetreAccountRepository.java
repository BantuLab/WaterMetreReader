package com.bantulogic.core.watermetrereader.Data.Repository;

import android.app.Application;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.AppDatabase;
import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.DAO.MetreAccountDAO;
import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.MetreAccount;
import com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI.MetreAccountWebAPI;
import com.bantulogic.core.watermetrereader.Data.DataSource.NetworkRESTAPI.ServiceGenerator;

import java.util.List;

import androidx.lifecycle.LiveData;

public class MetreAccountRepository {
    private MetreAccountDAO mMetreAccountDAO;
    private MetreAccountWebAPI mMetreAccountWebAPI;

    private LiveData<List<MetreAccount>> mAllMetreAccounts;
    private LiveData<MetreAccount> mMetreAccount;

    public MetreAccountRepository(Application application){
        AppDatabase db = AppDatabase.getDatabse(application);
        mMetreAccountDAO = db.metreAccountDAO();
        mMetreAccountWebAPI = ServiceGenerator.createService(MetreAccountWebAPI.class);


        mAllMetreAccounts = mMetreAccountDAO.getAllMetreAccountsByUser("6767676");

    }
}
