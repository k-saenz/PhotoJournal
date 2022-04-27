package com.example.photojournal.models;

import java.time.LocalDateTime;

public class DigitalPhoto extends Photo{
    private String resolution;

    public DigitalPhoto(){}

    public DigitalPhoto(String id, String name, String description,
                        LocalDateTime dateTime, String location,
                        Exposure exposure, int shutterSpeed,
                        int aperture, String camera,
                        String lens, int iso, String res) {
        super(id, name, description, dateTime, location, exposure, shutterSpeed, aperture, iso, camera, lens);
        this.resolution = res;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}