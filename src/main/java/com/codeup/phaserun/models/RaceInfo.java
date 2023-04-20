package com.codeup.phaserun.models;

public class RaceInfo {

    private String name;

    private String description;

    private String date;

    private String logoUrl;

    private int raceId;

    private String raceURL;

    public RaceInfo() {
    }

    public RaceInfo(String name, String description, String date, String logoUrl, int raceId, String raceURL) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.logoUrl = logoUrl;
        this.raceId = raceId;
        this.raceURL = raceURL;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

//    Temporary "toString" method for profile bookmark rendering tests. Delete when no longer necessary. - Rob (20 April)

//    @Override
//    public String toString() {
//        return "RaceInfo{" +
//                "name='" + name + '\'' +
//                ", description='" + description + '\'' +
//                ", date='" + date + '\'' +
//                ", logoUrl='" + logoUrl + '\'' +
//                ", raceId=" + raceId +
//                ", raceURL='" + raceURL + '\'' +
//                '}';
//    }

//    End of temporary "toString"
}

