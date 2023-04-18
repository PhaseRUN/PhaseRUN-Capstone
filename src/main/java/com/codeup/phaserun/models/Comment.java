package com.codeup.phaserun.models;

import jakarta.persistence.*;

@Entity
@Table(name="comments")

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String body;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Race race;

    //contractors
    public Comment() {
    }

    public Comment(String body) {
        this.body = body;
    }

    public Comment(long id, String body) {
        this.id = id;
        this.body = body;
    }

    public Comment(long id, String body, User user) {
        this.id = id;
        this.body = body;
        this.user = user;
    }

    public Comment(String body, User user, Race race) {
        this.body = body;
        this.user = user;
        this.race = race;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    @Override
    public String toString() {
        return "Body{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", user=" + user +
                ", race=" + race +
                '}';
    }
}
