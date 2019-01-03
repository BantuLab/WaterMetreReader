package com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.DAO;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.Customer;
import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.MetreAccount;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface CustomerDAO {

    @Query("SELECT * FROM Customer")
    LiveData<List<Customer>> getAllCustomers();

    @Query(("SELECT * FROM Customer WHERE id = :user_id"))
    LiveData<List<Customer>> getAllCustomersByUserId(String user_id);

    @Query(("SELECT * FROM Customer WHERE id = :id LIMIT 1"))
    LiveData<Customer> getCustomerById(String id);

    @Insert
    void insertMany(Customer... customers);

    @Delete
    void deleteCustomer(Customer customer);

    @Query("DELETE FROM Customer")
    void deleteAllCustomers();
}
