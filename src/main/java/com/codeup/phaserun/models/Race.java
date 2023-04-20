package com.codeup.phaserun.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="races")
public class Race {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 128)
    private String raceId;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "comment")
    private List<Comment> comments;

    @ManyToMany(mappedBy = "races")
    private List<User> users;

    public Race() {
    }



    public Race(String raceId, List<User> users) {
        this.raceId = raceId;
        this.users = users;
    }

    public Race(String raceId) {
        this.raceId = raceId;
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

//    @Override
//    public String toString() {
//        return "Race{" +
//                "raceId='" + raceId + '\'' +
//                ", users=" + users +
//                '}';
//    }
}
