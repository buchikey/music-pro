package com.example.musicpro.data;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class Venue implements Serializable {
    private String id,name, address, time;
    private Double lat, lon;
    public Venue() {
    }

    public Venue(String name, String address, String time) {
        this.name = name;
        this.address = address;
        this.time = time;
    }

    public Venue(String id, String name, String address, String time) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.time = time;
    }

    public Venue(String name, String address, Double lat, Double lon) {
        this.name = name;
        this.address = address;
        this.lat = lat;
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @NonNull
    @Override
    public String toString() {
        return "{" +
                "id : " +id +
                ", name : " +name+
                ", address :" +address+
                ", opening_time :" + time+
                "}";
    }
}
