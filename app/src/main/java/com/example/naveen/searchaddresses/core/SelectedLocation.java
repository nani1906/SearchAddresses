package com.example.naveen.searchaddresses.core;

public class SelectedLocation {
    private int id;
    private String description;
    private String title;
    private Double latitude;
    private Double longitude;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "SelectedLocation{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", description='" + description + '\'' +
                '}';
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }


    // This is a custom constructor for creating new objects with all its private fields.

    public SelectedLocation(int id, Double latitude, Double longitude, String description, String title) {
        this.title = title;
        this.description = description;
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public SelectedLocation() {

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
