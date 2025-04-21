package com.cinetickets.dao;

import com.cinetickets.model.Movie;
import com.cinetickets.model.Showtime;
import com.cinetickets.util.DatabaseUtil;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShowtimeDAO {
    
    private MovieDAO movieDAO = new MovieDAO();
    
    public List<Showtime> getShowtimesByMovieId(int movieId) {
        List<Showtime> showtimes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM showtimes WHERE movie_id = ? ORDER BY showtime";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movieId);
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Showtime showtime = mapResultSetToShowtime(rs);
                showtimes.add(showtime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return showtimes;
    }
    
    public List<Showtime> getShowtimesByMovieAndDate(int movieId, LocalDate date) {
        List<Showtime> showtimes = new ArrayList<>();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM showtimes WHERE movie_id = ? AND DATE(showtime) = ? ORDER BY showtime";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, movieId);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            rs = stmt.executeQuery();
            
            while (rs.next()) {
                Showtime showtime = mapResultSetToShowtime(rs);
                showtimes.add(showtime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return showtimes;
    }
    
    public Showtime getShowtimeById(int id) {
        Showtime showtime = null;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            conn = DatabaseUtil.getConnection();
            String sql = "SELECT * FROM showtimes WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            
            if (rs.next()) {
                showtime = mapResultSetToShowtime(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(conn, stmt, rs);
        }
        
        return showtime;
    }
    
    private Showtime mapResultSetToShowtime(ResultSet rs) throws SQLException {
        Showtime showtime = new Showtime();
        showtime.setId(rs.getInt("id"));
        showtime.setMovieId(rs.getInt("movie_id"));
        showtime.setTheaterId(rs.getInt("theater_id"));
        
        Timestamp timestamp = rs.getTimestamp("showtime");
        showtime.setShowtime(timestamp.toLocalDateTime());
        
        // Load the associated movie
        Movie movie = movieDAO.getMovieById(showtime.getMovieId());
        showtime.setMovie(movie);
        
        return showtime;
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