package com.codeup.phaserun.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="races")
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 128)
    private String raceId;
    @Column(nullable = false, length = 300)
    private String raceName;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false, columnDefinition = "DECIMAL(6, 2)")
    private double CostInDollars;
    @Column(nullable = false, length = 30)
    private String distanceInKm;
    @Column(nullable = false, columnDefinition = "DATE")
    private Date raceStart;
    @Column(nullable = false, length = 2)
    private String state;
    @Column(nullable = false)
    private int zipcode;
    @Column(nullable = false, length = 128)
    private String city;

    @Column(nullable = false, length = 256)
    private String url;

    @Column(nullable = false, length = 256)
    private String logoUrl;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "comment")
    private List<Comment> comments;

    @ManyToMany(mappedBy = "races")
    private List<User> users;


    public Race(int id, String raceId, String raceName, String description, double CostInDollars, String distanceInKm, Date raceStart, String state, int zipcode, String city, List<User> users) {
        this.id = id;
        this.raceId = raceId;
        this.raceName = raceName;
        this.description = description;
        this.CostInDollars = CostInDollars;
        this.distanceInKm = distanceInKm;
        this.raceStart = raceStart;
        this.state = state;
        this.zipcode = zipcode;
        this.city = city;
        this.users = users;
    }

    public Race(String raceId, String raceName, String description, double CostInDollars, String distanceInKm, Date raceStart, String state, int zipcode, String city, List<User> users) {
        this.raceId = raceId;
        this.raceName = raceName;
        this.description = description;
        this.CostInDollars = CostInDollars;
        this.distanceInKm = distanceInKm;
        this.raceStart = raceStart;
        this.state = state;
        this.zipcode = zipcode;
        this.city = city;
        this.users = users;
    }

    public Race(String raceId, String raceName, String description, String state, int zipcode, String city)
    {
        this.raceId = raceId;
        this.raceName = raceName;
        this.description = description;
        this.state = state;
        this.zipcode = zipcode;
        this.city = city;
    }

    public Race() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRaceId() {
        return raceId;
    }

    public void setRaceId(String raceId) {
        this.raceId = raceId;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCostinDollars() {
        return CostInDollars;
    }

    public void setCostInDollars(double CostInDollars) {
        this.CostInDollars = CostInDollars;
    }

    public String getDistanceInKm() {
        return distanceInKm;
    }

    public void setDistanceInKm(String distanceInKm) {
        this.distanceInKm = distanceInKm;
    }

    public Date getRaceStart() {
        return raceStart;
    }

    public void setRaceStart(Date raceStart) {
        this.raceStart = raceStart;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    @Override
    public String toString() {
        return "Race{" +
                "raceId='" + raceId + '\'' +
                ", raceName='" + raceName + '\'' +
                ", description='" + description + '\'' +
                ", CostInDollars=" + CostInDollars +
                ", distanceInKm='" + distanceInKm + '\'' +
                ", raceStart=" + raceStart +
                ", state='" + state + '\'' +
                ", zipcode=" + zipcode +
                ", city='" + city + '\'' +
                ", url='" + url + '\'' +
                ", logoUrl='" + logoUrl + '\'' +
                ", users=" + users +
                '}';
    }
}
