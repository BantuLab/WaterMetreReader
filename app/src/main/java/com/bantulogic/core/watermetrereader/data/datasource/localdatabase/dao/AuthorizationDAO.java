package com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Authorization;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface AuthorizationDAO {

    @Query("SELECT * FROM Authorization WHERE (username = :username AND password = :password) LIMIT 1")
    LiveData<Authorization> login(String username, String password);

    @Query("SELECT * FROM Authorization LIMIT 1")
    LiveData<Authorization> getLoggedInUser();

    @Insert(onConflict = REPLACE)
    void insertAuth(Authorization authorization);

    @Delete
    void invalidateAuth(Authorization authorization);

    @Query("DELETE FROM Authorization")
    void deleteAllAuths();
}
