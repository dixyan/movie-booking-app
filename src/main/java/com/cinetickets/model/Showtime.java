package com.cinetickets.model;

import java.time.LocalDateTime;

public class Showtime {
    private int id;
    private int movieId;
    private int theaterId;
    private LocalDateTime showtime;
    private Movie movie; // For convenience
    
    // Constructors
    public Showtime() {}
    
    public Showtime(int id, int movieId, int theaterId, LocalDateTime showtime) {
        this.id = id;
        this.movieId = movieId;
        this.theaterId = theaterId;
        this.showtime = showtime;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getMovieId() {
        return movieId;
    }
    
    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }
    
    public int getTheaterId() {
        return theaterId;
    }
    
    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }
    
    public LocalDateTime getShowtime() {
        return showtime;
    }
    
    public void setShowtime(LocalDateTime showtime) {
        this.showtime = showtime;
    }
    
    public Movie getMovie() {
        return movie;
    }
    
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    
    // Convenience methods
    public String getFormattedTime() {
        int hour = showtime.getHour();
        int minute = showtime.getMinute();
        String period = hour >= 12 ? "PM" : "AM";
        hour = hour > 12 ? hour - 12 : (hour == 0 ? 12 : hour);
        return String.format("%d:%02d %s", hour, minute, period);
    }
    
    public String getFormattedDate() {
        return showtime.toLocalDate().toString();
    }
}