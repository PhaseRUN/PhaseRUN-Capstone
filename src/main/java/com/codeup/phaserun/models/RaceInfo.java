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

    private String zipcode;

    private String city;

    private String state;

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

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public static String redYellowGreen(Date compareDate, Date yellowBeginDate, Date yellowEndDate)
    {
        if(compareDate.compareTo(yellowBeginDate) > 0 && compareDate.compareTo(yellowEndDate) < 0)//if the date we're comparing is in between the two dates
        {
            System.out.println("Yellow");
            return "Yellow";
        }
        else if(compareDate.compareTo(yellowBeginDate) < 0)//if the date we're comparing is before the yellow range start date
        {
            System.out.println("Red");
            return "Red";
        }
        else if(compareDate.compareTo(yellowEndDate) > 0)//if the date we're comparing is after the yellow range end date
        {
            System.out.println("Green");
            return "Green";
        }

        return "probably didnt work ";
    }

}

