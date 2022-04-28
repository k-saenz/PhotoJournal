package com.example.photojournal.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.photojournal.models.Photo;

import java.util.List;

@Dao
public interface PhotoDAO {
    @Query("SELECT * FROM photos")
    List<Photo> getPhotos();

    @Query("SELECT * FROM photos " +
            "WHERE id = (:photoId)")
    Photo getPhoto(int photoId);

    @Insert
    void addPhoto(Photo photo);
    @Delete
    void deletePhoto(Photo photo);
}
