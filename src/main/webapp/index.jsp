<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>CineTickets - Book Movie Tickets Online</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/styles.css">
  <link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap">
  <script src="https://unpkg.com/feather-icons"></script>
</head>
<body>
  <div class="container">
    <header>
      <div class="header-content">
        <div>
          <h1>CineTickets</h1>
          <p class="text-muted">Book your favorite movies with ease</p>
        </div>
        <div class="header-buttons">
          <button class="btn btn-outline">Sign In</button>
          <button class="btn btn-primary">Sign Up</button>
        </div>
      </div>
    </header>

    <div class="tabs">
      <div class="tabs-list">
        <button class="tab-trigger active" data-tab="now-showing">Now Showing</button>
        <button class="tab-trigger" data-tab="coming-soon">Coming Soon</button>
      </div>
      
      <div class="tab-content active" id="now-showing">
        <div class="movie-grid">
          <c:forEach var="movie" items="${nowShowingMovies}">
            <div class="movie-card">
              <div class="movie-poster">
                <img src="${movie.posterUrl}" alt="${movie.title}">
                <c:if test="${not empty movie.rating}">
                  <span class="movie-rating">${movie.rating}</span>
                </c:if>
              </div>
              <div class="movie-info">
                <h3 class="movie-title">${movie.title}</h3>
                <div class="movie-meta">
                  <span class="movie-duration">${movie.duration}</span>
                  <span class="separator">•</span>
                  <span class="movie-genre">${movie.genre}</span>
                </div>
                <a href="${pageContext.request.contextPath}/movies/${movie.id}" class="btn btn-primary book-btn">Book Tickets</a>
              </div>
            </div>
          </c:forEach>
        </div>
      </div>
      
      <div class="tab-content" id="coming-soon">
        <div class="movie-grid">
          <c:forEach var="movie" items="${comingSoonMovies}">
            <div class="movie-card">
              <div class="movie-poster">
                <img src="${movie.posterUrl}" alt="${movie.title}">
              </div>
              <div class="movie-info">
                <h3 class="movie-title">${movie.title}</h3>
                <div class="movie-meta">
                  <span class="movie-duration">${movie.duration}</span>
                  <span class="separator">•</span>
                  <span class="movie-genre">${movie.genre}</span>
                </div>
                <button class="btn btn-outline book-btn" disabled>Coming Soon</button>
              </div>
            </div>
          </c:forEach>
        </div>
      </div>
    </div>
  </div>

  <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>