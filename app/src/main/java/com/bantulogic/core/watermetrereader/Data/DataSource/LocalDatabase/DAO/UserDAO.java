package com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.DAO;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM User")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM User WHERE user_id = :user_id  LIMIT 1")
    LiveData<User> getUserById(String user_id);

    @Insert
    void insertManyUsers(User... users);

    @Insert
    void insertUser(User user);

    @Delete
    void deleteUser(User user);

    @Query("DELETE FROM User")
    void deleteAllUsers();

    @Update
    void updateUser(User user);

    @Update
    void updateManyUsers(User... user);
}
