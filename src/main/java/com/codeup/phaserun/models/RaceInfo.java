package com.codeup.phaserun.models;

import java.util.Date;

public class RaceInfo {

    private String name;

    private String description;

    private String logoUrl;

    private int raceId;

    private String raceURL;


    private Date yellowDate;

    private Date greenDate;

    private Date raceDate;

    public RaceInfo() {
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

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public String getRaceURL() {
        return raceURL;
    }

    public void setRaceURL(String raceURL) {
        this.raceURL = raceURL;
    }

    public Date getYellowStartDate() {
        return yellowDate;
    }

    public void setYellowStartDate(Date startDate) {
        this.yellowDate = startDate;
    }

    public Date getGreenStartDate() {
        return greenDate;
    }

    public void setGreenStartDate(Date startDate) {
        this.greenDate = startDate;
    }

    public Date getRaceDate() {
        return raceDate;
    }

    public void setRaceDate(Date raceDate) {
        this.raceDate = raceDate;
    }
}

