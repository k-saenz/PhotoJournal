package com.example.photojournal.models;

public class Lens {
    private String id;
    private String model;
    private String focalLength;
    private String minAperture;

    public Lens() {
    }

    public Lens(String id, String model, String focalLength, String minAperture) {
        this.id = id;
        this.model = model;
        this.focalLength = focalLength;
        this.minAperture = minAperture;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getFocalLength() {
        return focalLength;
    }

    public void setFocalLength(String focalLength) {
        this.focalLength = focalLength;
    }

    public String getMinAperture() {
        return minAperture;
    }

    public void setMinAperture(String minAperture) {
        this.minAperture = minAperture;
    }
}