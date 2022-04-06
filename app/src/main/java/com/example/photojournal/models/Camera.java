package com.example.photojournal.models;

import java.util.List;

public class Camera {
    private String id;
    private String cameraModel;
    private List<Lens> lenses;

    public Camera() {
    }

    public Camera(String id, String cameraModel, List<Lens> lenses) {
        this.id = id;
        this.cameraModel = cameraModel;
        this.lenses = lenses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCameraModel() {
        return cameraModel;
    }

    public void setCameraModel(String cameraModel) {
        this.cameraModel = cameraModel;
    }

    public List<Lens> getLenses() {
        return lenses;
    }

    public void setLenses(List<Lens> lenses) {
        this.lenses = lenses;
    }
}