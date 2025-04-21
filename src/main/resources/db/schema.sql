CREATE DATABASE IF NOT EXISTS cinetickets;
USE cinetickets;

CREATE TABLE movies (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    poster_url VARCHAR(255),
    rating VARCHAR(10),
    rating_score DECIMAL(3,1),
    duration VARCHAR(20),
    genre VARCHAR(100),
    description TEXT,
    cast TEXT,
    director VARCHAR(255),
    is_showing BOOLEAN DEFAULT TRUE
);

CREATE TABLE theaters (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255)
);

CREATE TABLE showtimes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT,
    theater_id INT,
    showtime DATETIME NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movies(id),
    FOREIGN KEY (theater_id) REFERENCES theaters(id)
);

CREATE TABLE seats (
    id INT AUTO_INCREMENT PRIMARY KEY,
    theater_id INT,
    row_name CHAR(1) NOT NULL,
    seat_number INT NOT NULL,
    FOREIGN KEY (theater_id) REFERENCES theaters(id),
    UNIQUE KEY (theater_id, row_name, seat_number)
);

CREATE TABLE bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    booking_reference VARCHAR(10) NOT NULL,
    showtime_id INT,
    customer_name VARCHAR(255),
    customer_email VARCHAR(255),
    booking_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10,2),
    FOREIGN KEY (showtime_id) REFERENCES showtimes(id),
    UNIQUE KEY (booking_reference)
);

CREATE TABLE booked_seats (
    id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT,
    seat_id INT,
    FOREIGN KEY (booking_id) REFERENCES bookings(id),
    FOREIGN KEY (seat_id) REFERENCES seats(id),
    UNIQUE KEY (booking_id, seat_id)
);

-- Insert sample data
INSERT INTO movies (title, poster_url, rating, rating_score, duration, genre, description, cast, director, is_showing) VALUES
('Interstellar: Beyond Time', 'https://via.placeholder.com/300x450', 'PG-13', 8.7, '2h 45m', 'Sci-Fi/Adventure', 'A team of explorers travel through a wormhole in space in an attempt to ensure humanity\'s survival.', 'Matthew McConaughey, Anne Hathaway, Jessica Chastain, Michael Caine', 'Christopher Nolan', TRUE),
('The Last Guardian', 'https://via.placeholder.com/300x450', 'PG', 7.9, '1h 55m', 'Fantasy/Adventure', 'A young boy forms an extraordinary bond with a mysterious creature in a breathtaking fantasy world.', 'Emma Watson, Tom Holland, Ian McKellen, Idris Elba', 'Guillermo del Toro', TRUE),
('Midnight in Paris', 'https://via.placeholder.com/300x450', 'R', 7.5, '2h 10m', 'Romance/Drama', 'A screenwriter travels back in time to 1920s Paris through a mysterious car that appears at midnight.', 'Owen Wilson, Rachel McAdams, Marion Cotillard', 'Woody Allen', TRUE),
('The Silent Echo', 'https://via.placeholder.com/300x450', 'PG-13', 8.1, '2h 5m', 'Thriller/Mystery', 'A detective with a troubled past investigates a series of mysterious disappearances in a small town.', 'Jake Gyllenhaal, Zoe Saldana, John Malkovich', 'Denis Villeneuve', TRUE),
('Eternal Sunshine', 'https://via.placeholder.com/300x450', 'PG-13', 8.3, '2h 15m', 'Drama/Romance', 'A couple undergoes a procedure to erase each other from their memories after their relationship turns sour.', 'Ryan Gosling, Emma Stone, Tilda Swinton', 'Michel Gondry', FALSE);

INSERT INTO theaters (name, location) VALUES
('CineTickets Main Theater', 'Downtown, City Center'),
('CineTickets IMAX', 'West Mall, Suburb');

-- Insert seats for theater 1 (rows A-H, seats 1-10 per row)
INSERT INTO seats (theater_id, row_name, seat_number)
SELECT 1, char(64 + row_num), seat_num
FROM (SELECT 1 AS row_num UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8) AS rows
CROSS JOIN (SELECT 1 AS seat_num UNION SELECT 2 UNION SELECT 3 UNION SELECT 4 UNION SELECT 5 UNION SELECT 6 UNION SELECT 7 UNION SELECT 8 UNION SELECT 9 UNION SELECT 10) AS seats;

-- Insert showtimes for today, tomorrow, and day after
INSERT INTO showtimes (movie_id, theater_id, showtime)
SELECT m.id, 1, DATE_ADD(CURRENT_DATE(), INTERVAL h HOUR)
FROM movies m
CROSS JOIN (SELECT 10 AS h UNION SELECT 12 UNION SELECT 15 UNION SELECT 17 UNION SELECT 20 UNION SELECT 22) AS hours
WHERE m.is_showing = TRUE;

INSERT INTO showtimes (movie_id, theater_id, showtime)
SELECT m.id, 1, DATE_ADD(DATE_ADD(CURRENT_DATE(), INTERVAL 1 DAY), INTERVAL h HOUR)
FROM movies m
CROSS JOIN (SELECT 10 AS h UNION SELECT 12 UNION SELECT 15 UNION SELECT 17 UNION SELECT 20 UNION SELECT 22) AS hours
WHERE m.is_showing = TRUE;

INSERT INTO showtimes (movie_id, theater_id, showtime)
SELECT m.id, 1, DATE_ADD(DATE_ADD(CURRENT_DATE(), INTERVAL 2 DAY), INTERVAL h HOUR)
FROM movies m
CROSS JOIN (SELECT 10 AS h UNION SELECT 12 UNION SELECT 15 UNION SELECT 17 UNION SELECT 20 UNION SELECT 22) AS hours
WHERE m.is_showing = TRUE;