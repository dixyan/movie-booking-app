<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Select Seats - CineTickets</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap">
  <script src="https://unpkg.com/feather-icons"></script>
</head>
<body>
  <div class="container">
    <a href="${pageContext.request.contextPath}/movies/${showtime.movie.id}" class="back-link">← Back to movie</a>

    <div class="booking-layout">
      <div class="booking-main">
        <h1>Select Your Seats</h1>
        <div class="booking-meta">
          <div class="meta-item">
            <i data-feather="calendar"></i>
            <fmt:parseDate value="${showtime.showtime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" />
            <span><fmt:formatDate value="${parsedDate}" pattern="EEEE, MMMM d, yyyy" /></span>
          </div>
          <span class="separator">•</span>
          <div class="meta-item">
            <i data-feather="clock"></i>
            <span><fmt:formatDate value="${parsedDate}" pattern="h:mm a" /></span>
          </div>
          <span class="separator">•</span>
          <div class="meta-item">
            <i data-feather="map-pin"></i>
            <span>CineTickets Main Theater</span>
          </div>
        </div>

        <c:if test="${not empty error}">
          <div class="error-message">${error}</div>
        </c:if>

        <div class="seat-selection">
          <div class="seat-legend">
            <div class="legend-item">
              <div class="seat-icon available"></div>
              <span>Available</span>
            </div>
            <div class="legend-item">
              <div class="seat-icon selected"></div>
              <span>Selected</span>
            </div>
            <div class="legend-item">
              <div class="seat-icon taken"></div>
              <span>Taken</span>
            </div>
          </div>

          <div class="theater-layout">
            <div class="screen">SCREEN</div>
            
            <form id="booking-form" action="${pageContext.request.contextPath}/booking/seats" method="post">
              <input type="hidden" name="action" value="book">
              
              <div class="seats-container">
                <c:forEach var="row" items="A,B,C,D,E,F,G,H">
                  <div class="seat-row">
                    <div class="row-label">${row}</div>
                    
                    <c:forEach var="seatNum" begin="1" end="10">
                      <c:set var="seatLabel" value="${row}${seatNum}" />
                      <c:set var="isTaken" value="false" />
                      <c:set var="seatId" value="0" />
                      
                      <c:forEach var="seat" items="${seats}">
                        <c:if test="${seat.rowName == row.charAt(0) && seat.seatNumber == seatNum}">
                          <c:set var="seatId" value="${seat.id}" />
                          <c:if test="${seat.booked}">
                            <c:set var="isTaken" value="true" />
                          </c:if>
                        </c:if>
                      </c:forEach>
                      
                      <c:choose>
                        <c:when test="${isTaken}">
                          <div class="seat taken">${seatNum}</div>
                        </c:when>
                        <c:otherwise>
                          <div class="seat available" data-seat-id="${seatId}" data-seat-label="${seatLabel}" onclick="toggleSeat(this)">
                            ${seatNum}
                          </div>
                        </c:otherwise>
                      </c:choose>
                    </c:forEach>
                    
                    <div class="row-label">${row}</div>
                  </div>
                </c:forEach>
              </div>
              
              <div id="selected-seats-hidden"></div>
              
              <div class="customer-info">
                <h3>Your Information</h3>
                <div class="form-group">
                  <label for="customerName">Name</label>
                  <input type="text" id="customerName" name="customerName" required>
                </div>
                <div class="form-group">
                  <label for="customerEmail">Email</label>
                  <input type="email" id="customerEmail" name="customerEmail" required>
                </div>
              </div>
            </form>
          </div>
        </div>
      </div>

      <div class="booking-sidebar">
        <div class="booking-summary">
          <div class="summary-header">
            <h2>Booking Summary</h2>
          </div>
          <div class="summary-content">
            <div class="movie-info-summary">
              <h3>${showtime.movie.title}</h3>
              <div class="movie-meta">${showtime.movie.rating} • ${showtime.movie.duration}</div>
            </div>
            
            <div class="booking-details">
              <div class="detail-item">
                <span class="detail-label">Date:</span>
                <span><fmt:formatDate value="${parsedDate}" pattern="EEEE, MMMM d, yyyy" /></span>
              </div>
              <div class="detail-item">
                <span class="detail-label">Time:</span>
                <span><fmt:formatDate value="${parsedDate}" pattern="h:mm a" /></span>
              </div>
              <div class="detail-item">
                <span class="detail-label">Theater:</span>
                <span>CineTickets Main Theater</span>
              </div>
            </div>
            
            <div class="divider"></div>
            
            <div class="selected-seats-summary">
              <div class="detail-item">
                <span class="detail-label">Selected Seats:</span>
                <span id="selected-seats-list">None</span>
              </div>
              
              <div class="price-item">
                <span>Regular Seats (<span id="seat-count">0</span>)</span>
                <span id="seats-price">$0.00</span>
              </div>
              
              <div class="price-item">
                <div class="tooltip-container">
                  <span>Booking Fee</span>
                  <div class="tooltip-icon">
                    <i data-feather="info"></i>
                    <div class="tooltip">A small fee to maintain our booking platform.</div>
                  </div>
                </div>
                <span id="booking-fee">$0.00</span>
              </div>
            </div>
            
            <div class="divider"></div>
            
            <div class="total-price">
              <span>Total</span>
              <span id="total-price">$0.00</span>
            </div>
          </div>
          <div class="summary-footer">
            <button class="btn btn-primary btn-block" id="checkout-btn" disabled onclick="submitForm()">Proceed to Payment</button>
          </div>
        </div>
        
        <div class="booking-note">
          <p>You can select up to 8 seats per booking.</p>
        </div>
      </div>
    </div>
  </div>

  <script>
    // Initialize Feather icons
    feather.replace();
    
    // Selected seats array
    let selectedSeats = [];
    
    function toggleSeat(seatElement) {
      const seatId = seatElement.getAttribute('data-seat-id');
      const seatLabel = seatElement.getAttribute('data-seat-label');
      
      if (seatElement.classList.contains('selected')) {
        // Deselect the seat
        seatElement.classList.remove('selected');
        seatElement.classList.add('available');
        
        // Remove from selected seats
        const index = selectedSeats.findIndex(seat => seat.id === seatId);
        if (index !== -1) {
          selectedSeats.splice(index, 1);
        }
      } else {
        // Check if max seats (8) are already selected
        if (selectedSeats.length >= 8) {
          alert('You can select up to 8 seats per booking.');
          return;
        }
        
        // Select the seat
        seatElement.classList.remove('available');
        seatElement.classList.add('selected');
        
        // Add to selected seats
        selectedSeats.push({
          id: seatId,
          label: seatLabel
        });
      }
      
      // Update the summary
      updateSummary();
    }
    
    function updateSummary() {
      const seatCount = selectedSeats.length;
      const seatPrice = seatCount * 12;
      const bookingFee = seatCount * 1.5;
      const totalPrice = seatPrice + bookingFee;
      
      // Sort seats by label
      selectedSeats.sort((a, b) => a.label.localeCompare(b.label));
      
      // Update seat list
      const seatLabels = selectedSeats.map(seat => seat.label);
      document.getElementById('selected-seats-list').textContent = seatLabels.length > 0 ? seatLabels.join(', ') : 'None';
      
      // Update seat count
      document.getElementById('seat-count').textContent = seatCount;
      
      // Update prices
      document.getElementById('seats-price').textContent = `$${seatPrice.toFixed(2)}`;
      document.getElementById('booking-fee').textContent = `$${bookingFee.toFixed(2)}`;
      document.getElementById('total-price').textContent = `$${totalPrice.toFixed(2)}`;
      
      // Enable/disable checkout button
      document.getElementById('checkout-btn').disabled = seatCount === 0;
      
      // Update hidden form fields
      const hiddenContainer = document.getElementById('selected-seats-hidden');
      hiddenContainer.innerHTML = '';
      
      selectedSeats.forEach(seat => {
        const input = document.createElement('input');
        input.type = 'hidden';
        input.name = 'selectedSeats';
        input.value = seat.id;
        hiddenContainer.appendChild(input);
      });
    }
    
    function submitForm() {
      if (selectedSeats.length === 0) {
        alert('Please select at least one seat.');
        return;
      }
      
      document.getElementById('booking-form').submit();
    }
  </script>
</body>
</html>