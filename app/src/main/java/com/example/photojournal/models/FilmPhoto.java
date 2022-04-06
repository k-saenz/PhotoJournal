package com.example.photojournal.models;

import java.time.LocalDateTime;

public class FilmPhoto extends Photo{
    private String filmUsed;

    public FilmPhoto(){}

    public FilmPhoto(String id, String description,
                     LocalDateTime dateTime, String location,
                     Exposure exposure, String shutterSpeed,
                     int aperture, Camera camera, Lens lens, String filmUsed) {
        super(id, description, dateTime, location, exposure, shutterSpeed, aperture, camera, lens);
        this.filmUsed = filmUsed;
    }

    public String getFilmUsed() {
        return filmUsed;
    }

    public void setFilmUsed(String filmUsed) {
        this.filmUsed = filmUsed;
    }
}