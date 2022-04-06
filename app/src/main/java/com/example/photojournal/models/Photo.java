package com.example.photojournal.models;

import java.time.LocalDateTime;

public abstract class Photo {
    private String id;
    private String description;
    private LocalDateTime dateTime;
    private String location;
    private Exposure exposure;
    //Camera settings
    private String shutterSpeed;
    private int aperture;
    private Camera camera;
    private Lens lens;

    public Photo(){}

    public Photo(String id, String description,
                 LocalDateTime dateTime, String location,
                 Exposure exposure, String shutterSpeed,
                 int aperture, Camera camera, Lens lens) {
        this.id = id;
        this.description = description;
        this.dateTime = dateTime;
        this.location = location;
        this.exposure = exposure;
        this.shutterSpeed = shutterSpeed;
        this.aperture = aperture;
        this.camera = camera;
        this.lens = lens;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Exposure getExposure() {
        return exposure;
    }

    public void setExposure(Exposure exposure) {
        this.exposure = exposure;
    }

    public String getShutterSpeed() {
        return shutterSpeed;
    }

    public void setShutterSpeed(String shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }

    public int getAperture() {
        return aperture;
    }

    public void setAperture(int aperture) {
        this.aperture = aperture;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Lens getLens() {
        return lens;
    }

    public void setLens(Lens lens) {
        this.lens = lens;
    }
}