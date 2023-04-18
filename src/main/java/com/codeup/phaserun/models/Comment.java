package com.codeup.phaserun.models;

import jakarta.persistence.*;

@Entity
@Table(name="comments")

public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String comment;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User user;

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Race race;

    //contractors
    public Comment() {
    }

    public Comment(String comment) {
        this.comment = comment;
    }

    public Comment(int id, String comment) {
        this.id = id;
        this.comment = comment;
    }

    public Comment(int id, String comment, User user) {
        this.id = id;
        this.comment = comment;
        this.user = user;
    }

    public Comment(String comment, User user, Race race) {
        this.comment = comment;
        this.user = user;
        this.race = race;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

}
