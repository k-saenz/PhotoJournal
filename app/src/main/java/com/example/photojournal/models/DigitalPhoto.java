package com.example.photojournal.models;

import java.time.LocalDateTime;

// TODO: Implement Factory Design Pattern by implementing this class
public class DigitalPhoto extends Photo{
    private String resolution;

    public DigitalPhoto(){}

    public DigitalPhoto(String resolution) {
        this.resolution = resolution;
    }

    public DigitalPhoto(int id, String name, String description, LocalDateTime dateTime, String location, String exposure, int shutterSpeed, float aperture, int iso, String lens, String camera, boolean isFilm, String filmOrRes, String resolution, String pathToPic) {
        super(id, name, description, dateTime, location, exposure, shutterSpeed, aperture, iso, lens, camera, isFilm, filmOrRes, pathToPic);
        this.resolution = resolution;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}