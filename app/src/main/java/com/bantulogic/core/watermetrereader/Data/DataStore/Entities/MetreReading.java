package com.bantulogic.core.watermetrereader.Data.DataStore.Entities;

import android.location.Address;

import com.bantulogic.core.watermetrereader.Utilities.Converters;

import java.util.Date;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
@TypeConverters(Converters.class)
public class MetreReading {
    @PrimaryKey
    public String reading_id;

    public long metre_id;
    public Date date_of_reading;
    public int start_reading;
    public int end_reading;
    public int consumption;
    @Embedded
    public Address reading_address;
    public String reading_completed_by;
    public Date time_reading_completed;
}
