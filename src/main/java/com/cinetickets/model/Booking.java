package com.cinetickets.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Booking {
    private int id;
    private String bookingReference;
    private int showtimeId;
    private String customerName;
    private String customerEmail;
    private LocalDateTime bookingDate;
    private double totalAmount;
    private List<Seat> bookedSeats;
    private Showtime showtime; // For convenience
    
    // Constructors
    public Booking() {
        this.bookedSeats = new ArrayList<>();
    }
    
    public Booking(int id, String bookingReference, int showtimeId, String customerName, 
                  String customerEmail, LocalDateTime bookingDate, double totalAmount) {
        this.id = id;
        this.bookingReference = bookingReference;
        this.showtimeId = showtimeId;
        this.customerName = customerName;
        this.customerEmail = customerEmail;
        this.bookingDate = bookingDate;
        this.totalAmount = totalAmount;
        this.bookedSeats = new ArrayList<>();
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getBookingReference() {
        return bookingReference;
    }
    
    public void setBookingReference(String bookingReference) {
        this.bookingReference = bookingReference;
    }
    
    public int getShowtimeId() {
        return showtimeId;
    }
    
    public void setShowtimeId(int showtimeId) {
        this.showtimeId = showtimeId;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerEmail() {
        return customerEmail;
    }
    
    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }
    
    public LocalDateTime getBookingDate() {
        return bookingDate;
    }
    
    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }
    
    public double getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public List<Seat> getBookedSeats() {
        return bookedSeats;
    }
    
    public void setBookedSeats(List<Seat> bookedSeats) {
        this.bookedSeats = bookedSeats;
    }
    
    public void addSeat(Seat seat) {
        this.bookedSeats.add(seat);
    }
    
    public Showtime getShowtime() {
        return showtime;
    }
    
    public void setShowtime(Showtime showtime) {
        this.showtime = showtime;
    }
    
    // Convenience methods
    public int getSeatCount() {
        return bookedSeats.size();
    }
    
    public double getTicketPrice() {
        return getSeatCount() * 12.0; // $12 per seat
    }
    
    public double getBookingFee() {
        return getSeatCount() * 1.5; // $1.50 per seat
    }
    
    public String getFormattedSeats() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bookedSeats.size(); i++) {
            sb.append(bookedSeats.get(i).getSeatLabel());
            if (i < bookedSeats.size() - 1) {
                sb.append(", ");
            }
        }
        return sb.toString();
    }
}