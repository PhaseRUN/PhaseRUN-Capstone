package com.codeup.phaserun;

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
    private String race_id;
    @Column(nullable = false, length = 300)
    private String race_name;
    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column(nullable = false, columnDefinition = "DECIMAL(6, 2)")
    private double cost_in_dollars;
    @Column(nullable = false, length = 30)
    private String distance_in_km;
    @Column(nullable = false, columnDefinition = "DATE")
    private Date race_start;
    @Column(nullable = false, length = 2)
    private String state;
    @Column(nullable = false)
    private int zipcode;
    @Column(nullable = false, length = 128)
    private String city;

    @Column(nullable = false, length = 256)
    private String url;

    @ManyToMany(mappedBy = "races")
    private List<User> users;

    public Race(int id, String race_id, String race_name, String description, double cost_in_dollars, String distance_in_km, Date race_start, String state, int zipcode, String city, List<User> users) {
        this.id = id;
        this.race_id = race_id;
        this.race_name = race_name;
        this.description = description;
        this.cost_in_dollars = cost_in_dollars;
        this.distance_in_km = distance_in_km;
        this.race_start = race_start;
        this.state = state;
        this.zipcode = zipcode;
        this.city = city;
        this.users = users;
    }

    public Race(String race_id, String race_name, String description, double cost_in_dollars, String distance_in_km, Date race_start, String state, int zipcode, String city, List<User> users) {
        this.race_id = race_id;
        this.race_name = race_name;
        this.description = description;
        this.cost_in_dollars = cost_in_dollars;
        this.distance_in_km = distance_in_km;
        this.race_start = race_start;
        this.state = state;
        this.zipcode = zipcode;
        this.city = city;
        this.users = users;
    }

    public Race(String race_id, String race_name, String description, String state, int zipcode, String city)
    {
        this.race_id = race_id;
        this.race_name = race_name;
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

    public String getRace_id() {
        return race_id;
    }

    public void setRace_id(String race_id) {
        this.race_id = race_id;
    }

    public String getRace_name() {
        return race_name;
    }

    public void setRace_name(String race_name) {
        this.race_name = race_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getCost_in_dollars() {
        return cost_in_dollars;
    }

    public void setCost_in_dollars(double cost_in_dollars) {
        this.cost_in_dollars = cost_in_dollars;
    }

    public String getDistance_in_km() {
        return distance_in_km;
    }

    public void setDistance_in_km(String distance_in_km) {
        this.distance_in_km = distance_in_km;
    }

    public Date getRace_start() {
        return race_start;
    }

    public void setRace_start(Date race_start) {
        this.race_start = race_start;
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
}
