package com.bantulogic.core.watermetrereader.Data.Repository;

import android.app.Application;

import com.bantulogic.core.watermetrereader.Data.DataStore.DAO.MetreAccountDAO;
import com.bantulogic.core.watermetrereader.Data.DataStore.DAO.MetreReadingDAO;
import com.bantulogic.core.watermetrereader.Data.DataStore.DAO.UserDAO;
import com.bantulogic.core.watermetrereader.Data.DataStore.Database.AppDatabase;
import com.bantulogic.core.watermetrereader.Data.DataStore.Entities.MetreAccount;
import com.bantulogic.core.watermetrereader.Data.DataStore.Entities.MetreReading;
import com.bantulogic.core.watermetrereader.Data.DataStore.Entities.User;

import java.util.List;

import androidx.lifecycle.LiveData;

public class AppRepository {
    private UserDAO mUserDAO;
    private MetreAccountDAO mMetreAccountDAO;
    private MetreReadingDAO mMetreReadingDAO;

    private LiveData<List<User>> mAllUsers;
    private LiveData<List<MetreAccount>> mAllMetreAccounts;
    private LiveData<List<MetreReading>> mAllMetreReadings;

    private LiveData<User> mUser;
    private LiveData<MetreAccount> mMetreAccount;
    private LiveData<MetreReading> mMetreReading;

    public  AppRepository(Application application){
        AppDatabase db  = AppDatabase.getDatabse(application);

        mUserDAO = db.userDAO();
        mMetreAccountDAO = db.metreAccountDAO();
        mMetreReadingDAO = db.metreReadingDAO();

        //Lists
        mAllUsers = mUserDAO.getAllUsers();
        mAllMetreAccounts = mMetreAccountDAO.getAllMetreAccounts();
        mAllMetreReadings = mMetreReadingDAO.getAllMetreReadings();
    }
}
