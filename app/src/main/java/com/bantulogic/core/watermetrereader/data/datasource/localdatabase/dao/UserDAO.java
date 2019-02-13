package com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.User;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDAO {

    @Query("SELECT * FROM User")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM User WHERE user_id = :user_id  LIMIT 1")
    LiveData<User> getUser(String user_id);

    @Query("SELECT COUNT(*) FROM User WHERE user_id = :user_id AND (last_update - :timeout) >= 120000")
    int hasUser(String user_id, Long timeout); //Check last updated 2 mins ago

    @Insert(onConflict = REPLACE)
    void insertManyUsers(User... users);

    @Insert(onConflict = REPLACE)
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
