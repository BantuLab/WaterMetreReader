package com.bantulogic.core.watermetrereader.Data.DataStore.Entities;

import android.location.Address;

import com.bantulogic.core.watermetrereader.Utilities.Converters;

import java.util.UUID;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
@TypeConverters(Converters.class)
public class MetreAccount {
    @PrimaryKey
    public long metre_id;

    public String customer_id;
    @Embedded
    public Address metre_address;
    public String assigned_user_id;

    public MetreAccount(){
        //Do stuff
    }
}
