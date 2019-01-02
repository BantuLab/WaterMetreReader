package com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.DAO;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.Customer;
import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.MetreAccount;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

public interface CustomerDAO {

    @Query("SELECT * FROM Customer")
    LiveData<List<Customer>> getAllCustomers();

    @Query(("SELECT * FROM Customer WHERE id IN (:ids)"))
    LiveData<List<Customer>> getAllCustomersById(String[] ids);

    @Query(("SELECT * FROM Customer WHERE id = :id LIMIT 1"))
    LiveData<MetreAccount> getMetreAccountById(String id);

    @Insert
    void insertMany(Customer... customers);

    @Delete
    void deleteCustomer(Customer customer);
}
