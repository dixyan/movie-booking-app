<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Booking Confirmation - CineTickets</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap">
  <script src="https://unpkg.com/feather-icons"></script>
</head>
<body>
  <div class="container confirmation-container">
    <div class="confirmation-header">
      <div class="success-icon">
        <i data-feather="check-circle"></i>
      </div>
      <h1>Booking Confirmed!</h1>
      <p class="text-muted">Your tickets have been booked successfully.</p>
    </div>

    <div class="confirmation-card">
      <div class="card-header">
        <h2>
          <i data-feather="ticket"></i>
          Booking Details
        </h2>
      </div>
      <div class="card-content">
        <div class="movie-info-confirmation">
          <h3>${booking.showtime.movie.title}</h3>
          <div class="movie-meta">
            ${booking.showtime.movie.rating} • ${booking.showtime.movie.duration}
          </div>
        </div>
        
        <div class="booking-details">
          <div class="detail-item">
            <i data-feather="calendar"></i>
            <fmt:parseDate value="${booking.showtime.showtime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" />
            <span><fmt:formatDate value="${parsedDate}" pattern="EEEE, MMMM d, yyyy" /></span>
          </div>
          <div class="detail-item">
            <i data-feather="clock"></i>
            <span><fmt:formatDate value="${parsedDate}" pattern="h:mm a" /></span>
          </div>
          <div class="detail-item">
            <i data-feather="map-pin"></i>
            <span>CineTickets Main Theater</span>
          </div>
        </div>
        
        <div class="divider"></div>
        
        <div class="booking-info">
          <h4>Booking Information</h4>
          <div class="info-grid">
            <div class="info-label">Booking ID:</div>
            <div class="info-value">${booking.bookingReference}</div>
            <div class="info-label">Seats:</div>
            <div class="info-value">${booking.formattedSeats}</div>
            <div class="info-label">Number of Tickets:</div>
            <div class="info-value">${booking.seatCount}</div>
          </div>
        </div>
        
        <div class="divider"></div>
        
        <div class="payment-info">
          <h4>Payment Information</h4>
          <div class="info-grid">
            <div class="info-label">Ticket Price:</div>
            <div class="info-value">$${booking.ticketPrice}</div>
            <div class="info-label">Booking Fee:</div>
            <div class="info-value">$${booking.bookingFee}</div>
            <div class="info-label font-medium">Total Amount:</div>
            <div class="info-value font-medium">$${booking.totalAmount}</div>
            <div class="info-label">Payment Method:</div>
            <div class="info-value">Credit Card (ending in 1234)</div>
          </div>
        </div>
      </div>
      <div class="card-footer">
        <div class="important-info">
          <i data-feather="info"></i>
          <div>
            <p class="font-medium">Important Information</p>
            <ul>
              <li>Please arrive 15 minutes before the show time.</li>
              <li>Bring your booking ID for verification.</li>
              <li>Outside food and beverages are not allowed.</li>
              <li>Tickets cannot be refunded or exchanged.</li>
            </ul>
          </div>
        </div>
        
        <div class="action-buttons">
          <button class="btn btn-outline" onclick="window.print()">Print Tickets</button>
          <a href="${pageContext.request.contextPath}/movies" class="btn btn-primary">Back to Home</a>
        </div>
      </div>
    </div>
  </div>

  <script>
    // Initialize Feather icons
    feather.replace();
  </script>
</body>
</html>