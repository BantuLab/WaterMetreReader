package com.bantulogic.core.watermetrereader.helpers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Date;
import java.util.List;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public static Date toDate(Long dateLong){
        return dateLong == null ? null : new Date(dateLong);
    }
    @TypeConverter
    public static Long toLong(Date date){
        return date == null ? null  : date.getTime();
    }

    public static class ArrayToStringConverter{
        @TypeConverter
        public static List<String> restoreList(String listOfString) {
            return new Gson().fromJson(listOfString, new TypeToken<List<String>>() {}.getType());
        }

        @TypeConverter
        public static String saveList(List<String> listOfString) {
            return new Gson().toJson(listOfString);
        }
    }
}
