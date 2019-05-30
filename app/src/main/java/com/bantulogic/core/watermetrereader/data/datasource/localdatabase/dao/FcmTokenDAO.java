package com.bantulogic.core.watermetrereader.data.datasource.localdatabase.dao;

import com.bantulogic.core.watermetrereader.data.datasource.localdatabase.entities.FcmToken;

import androidx.room.Insert;

import static androidx.room.OnConflictStrategy.REPLACE;

public interface FcmTokenDAO {

    @Insert(onConflict = REPLACE)
    void insertFcmToken(FcmToken fcmToken);
}
