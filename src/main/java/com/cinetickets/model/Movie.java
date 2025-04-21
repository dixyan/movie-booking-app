package com.cinetickets.model;

public class Movie {
    private int id;
    private String title;
    private String posterUrl;
    private String rating;
    private double ratingScore;
    private String duration;
    private String genre;
    private String description;
    private String cast;
    private String director;
    private boolean isShowing;
    
    // Constructors
    public Movie() {}
    
    public Movie(int id, String title, String posterUrl, String rating, double ratingScore, 
                 String duration, String genre, String description, String cast, 
                 String director, boolean isShowing) {
        this.id = id;
        this.title = title;
        this.posterUrl = posterUrl;
        this.rating = rating;
        this.ratingScore = ratingScore;
        this.duration = duration;
        this.genre = genre;
        this.description = description;
        this.cast = cast;
        this.director = director;
        this.isShowing = isShowing;
    }
    
    // Getters and Setters
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
    
    public String getPosterUrl() {
        return posterUrl;
    }
    
    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }
    
    public String getRating() {
        return rating;
    }
    
    public void setRating(String rating) {
        this.rating = rating;
    }
    
    public double getRatingScore() {
        return ratingScore;
    }
    
    public void setRatingScore(double ratingScore) {
        this.ratingScore = ratingScore;
    }
    
    public String getDuration() {
        return duration;
    }
    
    public void setDuration(String duration) {
        this.duration = duration;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getCast() {
        return cast;
    }
    
    public void setCast(String cast) {
        this.cast = cast;
    }
    
    public String getDirector() {
        return director;
    }
    
    public void setDirector(String director) {
        this.director = director;
    }
    
    public boolean isShowing() {
        return isShowing;
    }
    
    public void setShowing(boolean showing) {
        isShowing = showing;
    }
}