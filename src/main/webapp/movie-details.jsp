<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${movie.title} - CineTickets</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap">
  <script src="https://unpkg.com/feather-icons"></script>
</head>
<body>
  <div class="container">
    <a href="${pageContext.request.contextPath}/movies" class="back-link">← Back to movies</a>

    <div class="movie-details">
      <div class="movie-poster-large">
        <img src="${movie.posterUrl}" alt="${movie.title}">
      </div>

      <div class="movie-info-detailed">
        <h1>${movie.title}</h1>
        <div class="movie-meta">
          <span>${movie.duration}</span>
          <span class="separator">•</span>
          <span>${movie.genre}</span>
          <span class="separator">•</span>
          <span>${movie.rating}</span>
        </div>

        <div class="movie-rating-score">
          <i data-feather="star" class="star-icon"></i>
          <span>${movie.ratingScore}/10</span>
        </div>

        <p class="text-muted">${movie.description}</p>

        <div class="movie-cast">
          <h2>Cast</h2>
          <p class="text-muted">${movie.cast}</p>
        </div>

        <div class="movie-director">
          <h2>Director</h2>
          <p class="text-muted">${movie.director}</p>
        </div>

        <div class="showtimes-tabs">
          <div class="tabs-list">
            <button class="tab-trigger active" data-tab="today">Today</button>
            <button class="tab-trigger" data-tab="tomorrow">Tomorrow</button>
            <button class="tab-trigger" data-tab="day-after">Day After</button>
          </div>
          
          <c:set var="today" value="<%= java.time.LocalDate.now() %>" />
          <c:set var="tomorrow" value="${today.plusDays(1)}" />
          <c:set var="dayAfter" value="${today.plusDays(2)}" />
          
          <div class="tab-content active" id="today">
            <div class="showtimes-grid">
              <c:set var="hasShowtimes" value="false" />
              <c:forEach var="showtime" items="${showtimes}">
                <c:if test="${showtime.showtime.toLocalDate().equals(today)}">
                  <c:set var="hasShowtimes" value="true" />
                  <a href="${pageContext.request.contextPath}/booking/seats?showtimeId=${showtime.id}" class="btn btn-outline">
                    <fmt:parseDate value="${showtime.showtime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" />
                    <fmt:formatDate value="${parsedDate}" pattern="h:mm a" />
                  </a>
                </c:if>
              </c:forEach>
              <c:if test="${!hasShowtimes}">
                <p class="text-muted">No showtimes available for today.</p>
              </c:if>
            </div>
          </div>
          
          <div class="tab-content" id="tomorrow">
            <div class="showtimes-grid">
              <c:set var="hasShowtimes" value="false" />
              <c:forEach var="showtime" items="${showtimes}">
                <c:if test="${showtime.showtime.toLocalDate().equals(tomorrow)}">
                  <c:set var="hasShowtimes" value="true" />
                  <a href="${pageContext.request.contextPath}/booking/seats?showtimeId=${showtime.id}" class="btn btn-outline">
                    <fmt:parseDate value="${showtime.showtime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" />
                    <fmt:formatDate value="${parsedDate}" pattern="h:mm a" />
                  </a>
                </c:if>
              </c:forEach>
              <c:if test="${!hasShowtimes}">
                <p class="text-muted">No showtimes available for tomorrow.</p>
              </c:if>
            </div>
          </div>
          
          <div class="tab-content" id="day-after">
            <div class="showtimes-grid">
              <c:set var="hasShowtimes" value="false" />
              <c:forEach var="showtime" items="${showtimes}">
                <c:if test="${showtime.showtime.toLocalDate().equals(dayAfter)}">
                  <c:set var="hasShowtimes" value="true" />
                  <a href="${pageContext.request.contextPath}/booking/seats?showtimeId=${showtime.id}" class="btn btn-outline">
                    <fmt:parseDate value="${showtime.showtime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" />
                    <fmt:formatDate value="${parsedDate}" pattern="h:mm a" />
                  </a>
                </c:if>
              </c:forEach>
              <c:if test="${!hasShowtimes}">
                <p class="text-muted">No showtimes available for the day after tomorrow.</p>
              </c:if>
            </div>
          </div>
        </div>

        <div class="theater-info">
          <i data-feather="map-pin"></i>
          <span>CineTickets Main Theater</span>
        </div>
      </div>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/movie-details.js"></script>
</body>
</html>