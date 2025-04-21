package com.cinetickets.controller;

import com.cinetickets.dao.MovieDAO;
import com.cinetickets.model.Movie;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/movies/*")
public class MovieServlet extends HttpServlet {
    
    private MovieDAO movieDAO = new MovieDAO();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        
        String pathInfo = request.getPathInfo();
        
        if (pathInfo == null || pathInfo.equals("/")) {
            // Get all movies for the homepage
            List<Movie> nowShowingMovies = movieDAO.getNowShowingMovies();
            List<Movie> comingSoonMovies = movieDAO.getComingSoonMovies();
            
            request.setAttribute("nowShowingMovies", nowShowingMovies);
            request.setAttribute("comingSoonMovies", comingSoonMovies);
            
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            try {
                // Get movie details by ID
                int movieId = Integer.parseInt(pathInfo.substring(1));
                Movie movie = movieDAO.getMovieById(movieId);
                
                if (movie != null) {
                    request.setAttribute("movie", movie);
                    request.getRequestDispatcher("/movie-details.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            } catch (NumberFormatException e) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}