package com.cinetickets.dao;

import com.cinetickets.model.Booking;
import com.cinetickets.model.Seat;
import com.cinetickets.model.Showtime;
import com.cinetickets.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BookingDAO {
    
    private ShowtimeDAO showtimeDAO = new ShowtimeDAO();
    
    public List<Seat> getAvailableSeats(int showtimeId) {
        List<Seat> availableSeats = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            
            // Get the theater ID for this showtime
            String theaterSql = "SELECT theater_id FROM showtimes WHERE id = ?";
            stmt = conn.prepareStatement(theaterSql);
            stmt.setInt(1, showtimeId);
            rs = stmt.executeQuery();
            
            int theaterId = 0;
            if (rs.next()) {
                theaterId = rs.getInt("theater_id");
            } else {
                return availableSeats; // Showtime not found
            }
            
            rs.close();
            stmt.close();
            
            // Get all seats for this theater
            String seatsSql = "SELECT s.* FROM seats s WHERE s.theater_id = ?";
            stmt = conn.prepareStatement(seatsSql);
            stmt.setInt(1, theaterId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Seat seat = new Seat();
                seat.setId(rs.getInt("id"));
                seat.setTheaterId(rs.getInt("theater_id"));
                seat.setRowName(rs.getString("row_name").charAt(0));
                seat.setSeatNumber(rs.getInt("seat_number"));
                seat.setBooked(false);
                availableSeats.add(seat);
            }
            
            rs.close();
            stmt.close();
            
            // Mark seats that are already booked for this showtime
            String bookedSql = "SELECT bs.seat_id FROM booked_seats bs " +
                              "JOIN bookings b ON bs.booking_id = b.id " +
                              "WHERE b.showtime_id = ?";
            stmt = conn.prepareStatement(bookedSql);
            stmt.setInt(1, showtimeId);
            rs = stmt.executeQuery();
            
            List<Integer> bookedSeatIds = new ArrayList<>();
            while (rs.next()) {
                bookedSeatIds.add(rs.getInt("seat_id"));
            }
            
            // Mark booked seats
            for (Seat seat : availableSeats) {
                if (bookedSeatIds.contains(seat.getId())) {
                    seat.setBooked(true);
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return availableSeats;
    }
    
    public Booking createBooking(int showtimeId, List<Integer> seatIds, String customerName, String customerEmail) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Booking booking = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            conn.setAutoCommit(false); // Start transaction
            
            // Generate booking reference
            String bookingRef = generateBookingReference();
            
            // Calculate total amount
            double ticketPrice = seatIds.size() * 12.0; // $12 per seat
            double bookingFee = seatIds.size() * 1.5;   // $1.50 per seat
            double totalAmount = ticketPrice + bookingFee;
            
            // Create booking record
            String sql = "INSERT INTO bookings (booking_reference, showtime_id, customer_name, " +
                         "customer_email, booking_date, total_amount) VALUES (?, ?, ?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, bookingRef);
            stmt.setInt(2, showtimeId);
            stmt.setString(3, customerName);
            stmt.setString(4, customerEmail);
            stmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setDouble(6, totalAmount);
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                throw new SQLException("Creating booking failed, no rows affected.");
            }
            
            rs = stmt.getGeneratedKeys();
            int bookingId;
            if (rs.next()) {
                bookingId = rs.getInt(1);
            } else {
                throw new SQLException("Creating booking failed, no ID obtained.");
            }
            
            rs.close();
            stmt.close();
            
            // Add booked seats
            String seatSql = "INSERT INTO booked_seats (booking_id, seat_id) VALUES (?, ?)";
            stmt = conn.prepareStatement(seatSql);
            
            for (Integer seatId : seatIds) {
                stmt.setInt(1, bookingId);
                stmt.setInt(2, seatId);
                stmt.addBatch();
            }
            
            stmt.executeBatch();
            
            // Commit transaction
            conn.commit();
            
            // Create booking object to return
            booking = getBookingByReference(bookingRef);
            
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback transaction on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Reset auto-commit
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            closeResources(conn, stmt, rs);
        }
        
        return booking;
    }
    
    public Booking getBookingByReference(String reference) {
        Booking booking = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            
            // Get booking details
            String sql = "SELECT * FROM bookings WHERE booking_reference = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, reference);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                booking = new Booking();
                booking.setId(rs.getInt("id"));
                booking.setBookingReference(rs.getString("booking_reference"));
                booking.setShowtimeId(rs.getInt("showtime_id"));
                booking.setCustomerName(rs.getString("customer_name"));
                booking.setCustomerEmail(rs.getString("customer_email"));
                
                Timestamp timestamp = rs.getTimestamp("booking_date");
                booking.setBookingDate(timestamp.toLocalDateTime());
                
                booking.setTotalAmount(rs.getDouble("total_amount"));
                
                // Load the associated showtime
                Showtime showtime = showtimeDAO.getShowtimeById(booking.getShowtimeId());
                booking.setShowtime(showtime);
            } else {
                return null; // Booking not found
            }
            
            rs.close();
            stmt.close();
            
            // Get booked seats
            String seatsSql = "SELECT s.* FROM seats s " +
                             "JOIN booked_seats bs ON s.id = bs.seat_id " +
                             "JOIN bookings b ON bs.booking_id = b.id " +
                             "WHERE b.booking_reference = ?";
            stmt = conn.prepareStatement(seatsSql);
            stmt.setString(1, reference);
            rs = stmt.executeQuery();
            
            List<Seat> seats = new ArrayList<>();
            while (rs.next()) {
                Seat seat = new Seat();
                seat.setId(rs.getInt("id"));
                seat.setTheaterId(rs.getInt("theater_id"));
                seat.setRowName(rs.getString("row_name").charAt(0));
                seat.setSeatNumber(rs.getInt("seat_number"));
                seat.setBooked(true);
                seats.add(seat);
            }
            
            booking.setBookedSeats(seats);
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return booking;
    }
    
    private String generateBookingReference() {
        // Generate a random 8-character alphanumeric string
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 8; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }
    
    private void closeResources(Connection conn, Statement stmt, ResultSet rs) {
        try {
            if (rs != null) rs.close();
            if (stmt != null) stmt.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}