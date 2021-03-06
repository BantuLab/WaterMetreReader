package com.bantulogic.core.watermetrereader.data.repository;

import android.app.Application;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.AppDatabase;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao.CustomerDAO;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Customer;
import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.User;
import com.bantulogic.core.watermetrereader.data.datasource.network.CustomerWebAPI;
import com.bantulogic.core.watermetrereader.data.datasource.network.helpers.ServiceGenerator;

import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;

public class CustomerRepository {
    private CustomerDAO mCustomerDAO;
    private CustomerWebAPI mCustomerWebAPI;

    private LiveData<List<Customer>> mAllCustomers;
    private LiveData<Customer> mCustomer;

    public CustomerRepository(Application application){
        //Use some global method to get a token and currently logged-in user
        String authToken = "some token";
        //Ideally, this should be a call to the in-memory currently logged-in user
        User currentUser = new User("1212","weewe","eett","dffd","male",new Date(),"field_agent","dta_collector", new Date());
        //Local Cache
        AppDatabase db = AppDatabase.getDatabase(application);
        this.mCustomerDAO = db.customerDAO();
        this.mAllCustomers = db.customerDAO().getAllCustomers();
        this.mCustomer = db.customerDAO().getCustomerById(currentUser.getUserId());

        //Network REST API

        this.mCustomerWebAPI = ServiceGenerator.createService(CustomerWebAPI.class, authToken);
    }

    //region CUSTOMER API IMPLEMENTATION
    public LiveData<List<Customer>> getAllCustomers(){
        return this.mAllCustomers;
    }

    public LiveData<Customer> getCustomer() {
        return mCustomer;
    }
    //endregion
}
