package com.urosdragojevic.realbookstore.domain;

import java.util.ArrayList;
import java.util.List;

public class Book {

    private int id;

    private String title;

    private String description;

    private String author;

    private List<Genre> genres;

    private List<Comment> comments;

    private List<Rating> rating;

    private float overallRating = 0f;

    public Book() {
        this.genres = new ArrayList<>();
    }

    public Book(int id, String title, String description, String author) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.genres = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.rating = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Rating> getRating() {
        return rating;
    }

    public void setRating(List<Rating> rating) {
        this.rating = rating;
    }

    public float getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(float overallRating) {
        this.overallRating = overallRating;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", author='" + author + '\'' +
                ", genres=" + genres +
                ", comments=" + comments +
                ", rating=" + rating +
                ", overallRating=" + overallRating +
                '}';
    }
}
