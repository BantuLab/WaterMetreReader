package com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.DAO;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.MetreAccount;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MetreAccountDAO {

    @Query("SELECT * FROM MetreAccount")
    LiveData<List<MetreAccount>> getAllMetreAccounts();

    @Query(("SELECT * FROM MetreAccount WHERE metre_id IN (:metre_ids)"))
    LiveData<List<MetreAccount>> getAllMetreAccountsById(String[] metre_ids);

    @Query(("SELECT * FROM MetreAccount WHERE customer_id = :customer_id"))
    LiveData<List<MetreAccount>> getAllMetreAccountsByCustomer(long customer_id);

    @Query(("SELECT * FROM MetreAccount WHERE assigned_user_id = :assigned_user_id"))
    LiveData<List<MetreAccount>> getAllMetreAccountsByUser(String assigned_user_id);

    @Query(("SELECT * FROM MetreAccount WHERE metre_id = :metre_id LIMIT 1"))
    LiveData<MetreAccount> getMetreAccountById(String metre_id);

    @Insert
    void insertMany(MetreAccount... metreAccounts);

    @Delete
    void deleteMetreAccount(MetreAccount metreAccount);

    @Query("DELETE FROM MetreAccount")
    void deleteAllMetreAccounts();

}
