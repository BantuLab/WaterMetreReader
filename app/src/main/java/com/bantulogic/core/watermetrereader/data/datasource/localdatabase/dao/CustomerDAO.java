package com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Customer;

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
