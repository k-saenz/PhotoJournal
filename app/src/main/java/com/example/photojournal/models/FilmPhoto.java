package com.example.photojournal.models;

import java.time.LocalDateTime;

// TODO: Implement Factory Design Pattern by implementing this class
public class FilmPhoto extends Photo{
    private String filmUsed;

    public FilmPhoto(){}

    public FilmPhoto(String filmUsed) {
        this.filmUsed = filmUsed;
    }

    public FilmPhoto(int id, String name,
                     String description, LocalDateTime dateTime,
                     String location, Exposure exposure,
                     int shutterSpeed, float aperture,
                     int iso, String lens,
                     String camera, boolean isFilm,
                     String filmOrRes, String filmUsed) {
        super(id, name, description, dateTime, location, exposure, shutterSpeed, aperture, iso, lens, camera, isFilm, filmOrRes);
        this.filmUsed = filmUsed;
    }

    public String getFilmUsed() {
        return filmUsed;
    }

    public void setFilmUsed(String filmUsed) {
        this.filmUsed = filmUsed;
    }
}