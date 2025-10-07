package com.example.hellofx1;

import java.time.LocalDate;

public class venuesData {
    private int id;
    private String name;
    private String location;
    private int capacity;
    private String description;
    private String image;
    private LocalDate date;

    public venuesData(int id, String name, String location, int capacity,  String image, LocalDate date, String description) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = capacity;
        this.description = description;
        this.image = image;
        this.date = date;
    }

    public int getMovieId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getDescription(){
        return description;
    }

    public String getImage() {
        return image;
    }

    public LocalDate getDate() {
        return date;
    }


}
