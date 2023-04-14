package com.codeup.phaserun.models;

public class RaceInfo {

    private String name;

    private String description;

    private String address;

    private String date;

    private String logoUrl;

    public RaceInfo(String name, String description, String address, String date, String logoUrl) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.date = date;
        this.logoUrl = logoUrl;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogo_url(String logoUrl) {
        this.logoUrl = logoUrl;
    }
}