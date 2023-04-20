package com.codeup.phaserun.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="races")
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 128)
    private int raceId;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "body")
    private List<Comment> comments;

    @ManyToMany(mappedBy = "races")
    private List<User> users;

    public Race() {
    }

    public Race(int raceId, List<User> users) {
        this.raceId = raceId;
        this.users = users;
    }

    public Race(int raceId) {
        this.raceId = raceId;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getRaceId() {
        return raceId;
    }

    public void setRaceId(int raceId) {
        this.raceId = raceId;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
