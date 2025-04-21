package com.cinetickets.controller;

import com.cinetickets.dao.BookingDAO;
import com.cinetickets.dao.MovieDAO;
import com.cinetickets.dao.ShowtimeDAO;
import com.cinetickets.model.Booking;
import com.cinetickets.model.Movie;
import com.cinetickets.model.Seat;
import com.cinetickets.model.Showtime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/booking/*")
public class BookingServlet extends HttpServlet {
    
    private MovieDAO movieDAO = new MovieDAO();
    private ShowtimeDAO showtimeDAO = new ShowtimeDAO();
    private BookingDAO bookingDAO = new BookingDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            response.sendRedirect(request.getContextPath() + "/movies");
            return;
        }
        
        if (pathInfo.equals("/seats")) {
            // Show seat selection page
            String showtimeIdStr = request.getParameter("showtimeId");
            
            if (showtimeIdStr == null) {
                response.sendRedirect(request.getContextPath() + "/movies");
                return;
            }
            
            try {
                int showtimeId = Integer.parseInt(showtimeIdStr);
                Showtime showtime = showtimeDAO.getShowtimeById(showtimeId);
                
                if (showtime == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                
                // Get available seats
                List<Seat> seats = bookingDAO.getAvailableSeats(showtimeId);
                
                // Store in request attributes
                request.setAttribute("showtime", showtime);
                request.setAttribute("seats", seats);
                
                // Store in session for the booking process
                HttpSession session = request.getSession();
                session.setAttribute("showtimeId", showtimeId);
                
                request.getRequestDispatcher("/booking.jsp").forward(request, response);
                
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        
        if (action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }
        
        if (action.equals("book")) {
            // Process booking
            HttpSession session = request.getSession();
            Integer showtimeId = (Integer) session.getAttribute("showtimeId");
            
            if (showtimeId == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No showtime selected");
                return;
            }
            
            String[] selectedSeatsArray = request.getParameterValues("selectedSeats");
            
            if (selectedSeatsArray == null || selectedSeatsArray.length == 0) {
                request.setAttribute("error", "Please select at least one seat");
                doGet(request, response);
                return;
            }
            
            List<Integer> selectedSeatIds = Arrays.stream(selectedSeatsArray)
                                                 .map(Integer::parseInt)
                                                 .collect(Collectors.toList());
            
            String customerName = request.getParameter("customerName");
            String customerEmail = request.getParameter("customerEmail");
            
            if (customerName == null || customerName.trim().isEmpty() ||
                customerEmail == null || customerEmail.trim().isEmpty()) {
                request.setAttribute("error", "Please provide your name and email");
                doGet(request, response);
                return;
            }
            
            // Create booking
            Booking booking = bookingDAO.createBooking(showtimeId, selectedSeatIds, customerName, customerEmail);
            
            if (booking != null) {
                // Clear session data
                session.removeAttribute("showtimeId");
                
                // Redirect to confirmation page
                response.sendRedirect(request.getContextPath() + 
                                     "/confirmation?reference=" + booking.getBookingReference());
            } else {
                request.setAttribute("error", "Failed to create booking. Please try again.");
                doGet(request, response);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}