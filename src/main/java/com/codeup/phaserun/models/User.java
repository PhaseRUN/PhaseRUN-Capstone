package com.codeup.phaserun.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
@Table(name="users")
public class User {
    public enum RunningExpEnum {
        NONE, BEGINNER, RECREATIONAL, INTERMEDIATE, EXPERT
    }

    public enum ActivityLvlEnum {
        NONE, BEGINNER, INTERMEDIATE, EXPERT, ELITE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 128)
    private String username;
    @Column(nullable = false, length = 300)
    private String email;
    @Column(nullable = false, length = 128)
    private String password;
    @Enumerated(EnumType.ORDINAL)
    private RunningExpEnum runningExp;
    @Enumerated(EnumType.ORDINAL)
    private ActivityLvlEnum activityLvl;
    @Column(nullable = false, columnDefinition = "MEDIUMINT")
    private int zipcode;
    @Column(nullable = false)
    private Date birthDate;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_races",
            joinColumns = {@JoinColumn(name="user_id")},
            inverseJoinColumns = {@JoinColumn(name = "race_id")}
    )

    private List<Race> races;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "comment")
    private List<Comment> comments;

    public User(int id, String username, String email, String password, RunningExpEnum runningExp, ActivityLvlEnum activityLvl, int zipcode, Date birthDate, List<Race> races) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.runningExp = runningExp;
        this.activityLvl = activityLvl;
        this.zipcode = zipcode;
        this.birthDate = birthDate;
        this.races = races;
    }

    public User(String username, String email, String password, RunningExpEnum runningExp, ActivityLvlEnum activityLvl, int zipcode, Date birthDate, List<Race> races) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.runningExp = runningExp;
        this.activityLvl = activityLvl;
        this.zipcode = zipcode;
        this.birthDate = birthDate;
        this.races = races;
    }
    public User(String username, String email, String password, RunningExpEnum runningExp, ActivityLvlEnum activityLvl, int zipcode, Date birthDate) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.runningExp = runningExp;
        this.activityLvl = activityLvl;
        this.zipcode = zipcode;
        this.birthDate = birthDate;
    }

    public User(User copy) {
        id = copy.id; // This line is SUPER important! Many things won't work if it's absent
        email = copy.email;
        username = copy.username;
        password = copy.password;
        this.runningExp = runningExp;
        this.activityLvl = activityLvl;
        this.zipcode = zipcode;
        this.birthDate = birthDate;
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

    public RunningExpEnum getRunningExp() {
        return runningExp;
    }

    public void setRunningExp(RunningExpEnum runningExp) {
        this.runningExp = runningExp;
    }

    public ActivityLvlEnum getActivityLvl() {
        return activityLvl;
    }

    public void setActivityLvl(ActivityLvlEnum activityLvl) {
        this.activityLvl = activityLvl;
    }

    public int getZipcode() {
        return zipcode;
    }

    public void setZipcode(int zipcode) {
        this.zipcode = zipcode;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }


    public List<Race> getRaces() {
        return races;
    }

    public void setRaces(List<Race> races) {
        this.races = races;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", runningExp=" + runningExp +
                ", activityLvl=" + activityLvl +
                ", zipcode=" + zipcode +
                ", birthDate='" + birthDate + '\'' +
                ", races=" + races +
                '}';
    }
}
