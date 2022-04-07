package com.example.photojournal.models;

import java.time.LocalDateTime;

public class DigitalPhoto extends Photo{
    private int iso;

    public DigitalPhoto(String id, String name, String description,
                        LocalDateTime dateTime, String location,
                        Exposure exposure, int shutterSpeed,
                        int aperture, Camera camera,
                        Lens lens, int iso) {
        super(id, name, description, dateTime, location, exposure, shutterSpeed, aperture, camera, lens);
        this.iso = iso;
    }

    public int getIso() {
        return iso;
    }

    public void setIso(int iso) {
        this.iso = iso;
    }
}