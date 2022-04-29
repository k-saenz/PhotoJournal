package com.example.photojournal.data;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PhotoTypeConverters {

    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static String fromLocalDateTime(LocalDateTime dateTime){
        return dateTime.format(DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm"));
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @TypeConverter
    public static LocalDateTime toLocalDateTime(String date){
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm"));
    }
}
