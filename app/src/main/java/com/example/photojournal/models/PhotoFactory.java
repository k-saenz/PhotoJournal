package com.example.photojournal.models;

// TODO: Implement Factory Design Pattern by implementing this class
public class PhotoFactory {
    public static Photo createPhoto(String ph){
        switch (ph.toLowerCase()){
            case "digital":
                return new DigitalPhoto();
            case "film":
                return new FilmPhoto();
            default:
                return null;
        }
    }
}
