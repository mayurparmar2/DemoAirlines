package com.demo.example.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;
import java.util.List;

@Entity(tableName = "YourDataModel")
public class YourDataModel {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("_id")
    private String _id;

    @SerializedName("name")
    private String name;

    @SerializedName("trips")
    private int trips;

    @SerializedName("airline")
    private List<Airline> airline;

    @SerializedName("__v")
    private int __v;

    // Add an empty constructor for Room
    public YourDataModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTrips() {
        return trips;
    }

    public void setTrips(int trips) {
        this.trips = trips;
    }

    public List<Airline> getAirline() {
        return airline;
    }

    public void setAirline(List<Airline> airline) {
        this.airline = airline;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}

