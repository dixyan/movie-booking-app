package com.cinetickets.model;

public class Seat {
    private int id;
    private int theaterId;
    private char rowName;
    private int seatNumber;
    private boolean isBooked;
    
    // Constructors
    public Seat() {}
    
    public Seat(int id, int theaterId, char rowName, int seatNumber) {
        this.id = id;
        this.theaterId = theaterId;
        this.rowName = rowName;
        this.seatNumber = seatNumber;
        this.isBooked = false;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getTheaterId() {
        return theaterId;
    }
    
    public void setTheaterId(int theaterId) {
        this.theaterId = theaterId;
    }
    
    public char getRowName() {
        return rowName;
    }
    
    public void setRowName(char rowName) {
        this.rowName = rowName;
    }
    
    public int getSeatNumber() {
        return seatNumber;
    }
    
    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }
    
    public boolean isBooked() {
        return isBooked;
    }
    
    public void setBooked(boolean booked) {
        isBooked = booked;
    }
    
    // Convenience methods
    public String getSeatLabel() {
        return String.valueOf(rowName) + seatNumber;
    }
}