package com.bantulogic.core.watermetrereader.Data.DataStore.Entities;

import com.bantulogic.core.watermetrereader.Utilities.Converters;

import java.util.Date;
import java.util.UUID;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
@TypeConverters(Converters.class)
public class User {
    @NonNull
    @PrimaryKey
    public String user_id;
    public String username;
    public String first_name;
    public String last_name;
    public String sex;
    public Date date_of_birth;
    public String user_type;
    public String user_role;

    public User(){
    }
}
