package com.example.photojournal.data;

import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.room.TypeConverter;

import com.example.photojournal.models.Exposure;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class PhotoTypeConverters {
    @TypeConverter
    public static String exposureToString(Exposure e){
        switch (e){
            case OVER_EXPOSED:
                return "OVER_EXPOSED";
            case UNDER_EXPOSED:
                return "UNDER_EXPOSED";
            case WELL_EXPOSED:
                return "WELL_EXPOSED";
            default:
                return "";
        }
    }
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
