package com.example.photojournal.data;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.photojournal.models.Photo;

@Database(entities = {Photo.class}, version = 2)
@TypeConverters({PhotoTypeConverters.class})
public abstract class PhotoDB extends RoomDatabase {
    public abstract PhotoDAO photoDAO();
}
