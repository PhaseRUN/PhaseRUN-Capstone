package com.codeup.phaserun;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 128)
    private String username;
    @Column(nullable = false, length = 300)
    private String email;
    @Column(nullable = false, length = 128)
    private String password;
    @Column(nullable = false, columnDefinition = "ENUM(none, beginner, recreational, intermediate, expert)")
    private String running_exp;
    @Column(nullable = false, columnDefinition = "ENUM(none, beginner, intermediate, expert, elite)")
    private String fitness_lvl;
    @Column(nullable = false, columnDefinition = "SMALLINT")
    private int zipcode;
    @Column(nullable = false, columnDefinition = "DATE")
    private Date birth_date;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_races",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name = "race_id")}
    )
    private List<Race> races;

    public User(int id, String username, String email, String password, String running_exp, String fitness_lvl, int zipcode, Date birth_date, List<Race> races) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.running_exp = running_exp;
        this.fitness_lvl = fitness_lvl;
        this.zipcode = zipcode;
        this.birth_date = birth_date;
        this.races = races;
    }

    public User(String username, String email, String password, String running_exp, String fitness_lvl, int zipcode, Date birth_date, List<Race> races) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.running_exp = running_exp;
        this.fitness_lvl = fitness_lvl;
        this.zipcode = zipcode;
        this.birth_date = birth_date;
        this.races = races;
    }

    public User() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRunning_exp() {
        return running_exp;
    }

    public void setRunning_exp(String running_exp) {
        this.running_exp = running_exp;
    }

    public String getFitness_lvl() {
        return fitness_lvl;
    }

    public void setFitness_lvl(String fitness_lvl) {
        this.fitness_lvl = fitness_lvl;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public Date getBirth_date() {
        return birth_date;
    }

    public void setBirth_date(Date birth_date) {
        this.birth_date = birth_date;
    }

    public List<Race> getRaces() {
        return races;
    }

    public void setRaces(List<Race> races) {
        this.races = races;
    }
}
