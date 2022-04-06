package com.example.photojournal.models;

import java.time.LocalDateTime;

public class DigitalPhoto extends Photo{
    private String iso;

    public DigitalPhoto(String id, String description,
                        LocalDateTime dateTime, String location,
                        Exposure exposure, String shutterSpeed,
                        int aperture, Camera camera,
                        Lens lens, String iso) {
        super(id, description, dateTime, location, exposure, shutterSpeed, aperture, camera, lens);
        this.iso = iso;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }
}