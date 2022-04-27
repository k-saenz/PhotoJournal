package com.example.photojournal.models;

import java.time.LocalDateTime;

public class FilmPhoto extends Photo{
    private String filmUsed;

    public FilmPhoto(){}

    public FilmPhoto(String id, String name, String description,
                     LocalDateTime dateTime, String location,
                     Exposure exposure, int shutterSpeed,
                     int aperture, int iso, String camera, String lens, String filmUsed) {
        super(id, name, description, dateTime, location, exposure, shutterSpeed, aperture, iso, camera, lens);
        this.filmUsed = filmUsed;
    }

    public String getFilmUsed() {
        return filmUsed;
    }

    public void setFilmUsed(String filmUsed) {
        this.filmUsed = filmUsed;
    }
}