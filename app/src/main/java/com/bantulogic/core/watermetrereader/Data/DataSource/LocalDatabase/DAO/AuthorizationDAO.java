package com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.DAO;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.Authorization;
import com.bantulogic.core.watermetrereader.Helpers.Resource;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import retrofit2.http.GET;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface AuthorizationDAO {

    @Query("SELECT * FROM Authorization WHERE (username = :username AND password = :password) LIMIT 1")
    LiveData<Authorization> login(String username, String password);

    @Insert(onConflict = REPLACE)
    void insertAuth(Authorization authorization);

    @Delete
    void invalidateAuth(Authorization authorization);

    @Query("DELETE FROM Authorization")
    void deleteAllAuths();
}
