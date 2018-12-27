package com.bantulogic.core.watermetrereader.Data.DataStore.DAO;

import com.bantulogic.core.watermetrereader.Data.DataStore.Entities.User;

import java.util.List;
import java.util.UUID;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM User")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM User WHERE user_id IN (:userIds)")
    LiveData<List<User>> getAllUsers(String[] userIds);

    @Query("SELECT * FROM User WHERE user_id = (:user_id)  LIMIT 1")
    LiveData<User> getUserById(String user_id);

    @Insert
    void insertMany(User... users);

    @Delete
    void deleteUser(User user);
}
