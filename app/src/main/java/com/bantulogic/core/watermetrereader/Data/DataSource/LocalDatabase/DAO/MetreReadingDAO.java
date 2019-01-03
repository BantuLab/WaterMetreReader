package com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.DAO;

import com.bantulogic.core.watermetrereader.Data.DataSource.LocalDatabase.Entities.MetreReading;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MetreReadingDAO {

    @Query("SELECT * FROM MetreReading")
    LiveData<List<MetreReading>> getAllMetreReadings();

    @Query(("SELECT * FROM MetreReading WHERE reading_id IN (:reading_id)"))
    LiveData<List<MetreReading>> getAllMetreReadingsById(String[] reading_id);

    @Query(("SELECT * FROM MetreReading WHERE metre_id = (:metre_id)"))
    LiveData<List<MetreReading>> getAllMetreReadingsByMetre(long metre_id);

    @Query(("SELECT * FROM MetreReading WHERE metre_id = (:reading_id) LIMIT 1"))
    LiveData<MetreReading> getMetreReadingById(String reading_id);

    @Insert
    void insertMany(MetreReading... metreReadings);

    @Delete
    void deleteMetreReading(MetreReading metreReading);

    @Query("DELETE FROM MetreReading")
    void deleteAllMetreReadings();
}
