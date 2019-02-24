package com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.Authorization;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface AuthorizationDAO {

    @Query("SELECT * FROM Authorization LIMIT 1")
    LiveData<Authorization> getLoggedInUser();

    @Insert(onConflict = REPLACE)
    long insertAuth(Authorization authorization);

    @Update
    void logoutCurrentUser(Authorization authorization);

    @Delete
    void invalidateAuth(Authorization authorization);

    @Query("DELETE FROM Authorization")
    void deleteAllAuths();

}
