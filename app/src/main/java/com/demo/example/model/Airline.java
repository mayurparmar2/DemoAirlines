package com.demo.example.model;

import com.google.gson.annotations.SerializedName;

public class Airline {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    @SerializedName("country")
    private String country;

    @SerializedName("logo")
    private String logo;

    @SerializedName("slogan")
    private String slogan;

    @SerializedName("head_quaters")
    private String headQuarters;

    @SerializedName("website")
    private String website;

    @SerializedName("established")
    private String established;

    // Add an empty constructor for Room
    public Airline() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getHeadQuarters() {
        return headQuarters;
    }

    public void setHeadQuarters(String headQuarters) {
        this.headQuarters = headQuarters;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getEstablished() {
        return established;
    }

    public void setEstablished(String established) {
        this.established = established;
    }
}

