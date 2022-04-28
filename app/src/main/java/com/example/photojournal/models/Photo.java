package com.example.photojournal.models;

import java.io.Serializable;
import java.time.LocalDateTime;

// TODO: Implement Factory Design Pattern by making this class abstract
public class Photo implements Serializable {
    private String id;
    private String name;
    private String description;
    private LocalDateTime dateTime;
    private String location;
    private Exposure exposure;
    //Camera settings
    private int shutterSpeed;
    private float aperture;
    private int iso;
    private String lens;
    private String camera;
    // TODO: Remove these properties to implement Factory pattern
    private boolean isFilm; //true: Film, false: digital
    private String filmOrRes; //Film stock used or Resolution of digital image
    /*
    * FUTURE IMPLEMENTATION:
    * private Lens lens;
    * private Camera camera;
    * */

    public Photo(){}

    public Photo(String id, String name, String description,
                 LocalDateTime dateTime, String location,
                 Exposure exposure, int shutterSpeed,
                 float aperture, int iso, String lens,
                 String camera, boolean isFilm,
                 String filmOrRes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.location = location;
        this.exposure = exposure;
        this.shutterSpeed = shutterSpeed;
        this.aperture = aperture;
        this.iso = iso;
        this.lens = lens;
        this.camera = camera;
        this.isFilm = isFilm;
        this.filmOrRes = filmOrRes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getShutterSpeed() {
        return shutterSpeed;
    }

    public void setShutterSpeed(int shutterSpeed) {
        this.shutterSpeed = shutterSpeed;
    }

    public float getAperture() {
        return aperture;
    }

    public void setAperture(float aperture) {
        this.aperture = aperture;
    }

    public int getIso() {
        return iso;
    }

    public void setIso(int iso) {
        this.iso = iso;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getLens() {
        return lens;
    }

    public void setLens(String lens) {
        this.lens = lens;
    }

    public boolean isFilm() {
        return isFilm;
    }

    public void setFilm(boolean film) {
        isFilm = film;
    }

    public String getFilmOrRes() {
        return filmOrRes;
    }

    public void setFilmOrRes(String filmOrRes) {
        this.filmOrRes = filmOrRes;
    }
}