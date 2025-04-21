package com.cinetickets.controller;

import com.cinetickets.dao.BookingDAO;
import com.cinetickets.model.Booking;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/confirmation")
public class ConfirmationServlet extends HttpServlet {
    
    private BookingDAO bookingDAO = new BookingDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String reference = request.getParameter("reference");
        
        if (reference == null || reference.trim().isEmpty()) {
            response.sendRedirect(request.getContextPath() + "/movies");
            return;
        }
        
        Booking booking = bookingDAO.getBookingByReference(reference);
        
        if (booking == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Booking not found");
            return;
        }
        
        request.setAttribute("booking", booking);
        request.getRequestDispatcher("/confirmation.jsp").forward(request, response);
    }
}