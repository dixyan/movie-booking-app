document.addEventListener('DOMContentLoaded', function() {
  // Initialize Feather icons
  feather.replace();
  
  // Get parameters from URL
  const urlParams = new URLSearchParams(window.location.search);
  const movieId = urlParams.get('id') || '1';
  const seats = urlParams.get('seats') ? urlParams.get('seats').split(',') : [];
  const time = urlParams.get('time') || '3:00 PM';
  const date = urlParams.get('date') || 'today';
  
  // Generate booking ID
  const bookingId = generateBookingId();
  document.getElementById('booking-id').textContent = bookingId;
  
  // Load movie data
  loadMovieData(movieId);
  
  // Set confirmation details
  document.getElementById('confirmation-time').textContent = time;
  document.getElementById('confirmation-date').textContent = formatDate(date);
  document.getElementById('confirmation-seats').textContent = seats.sort().join(', ');
  document.getElementById('ticket-count').textContent = seats.length;
  
  // Calculate and set prices
  const ticketPrice = seats.length * 12;
  const bookingFee = seats.length * 1.5;
  const totalPrice = ticketPrice + bookingFee;
  
  document.getElementById('ticket-price').textContent = `$${ticketPrice.toFixed(2)}`;
  document.getElementById('confirmation-fee').textContent = `$${bookingFee.toFixed(2)}`;
  document.getElementById('confirmation-total').textContent = `$${totalPrice.toFixed(2)}`;
});

function loadMovieData(movieId) {
  // In a real app, this would be fetched from an API
  const movies = [
    {
      id: "1",
      title: "Interstellar: Beyond Time",
      rating: "PG-13",
      duration: "2h 45m",
    },
    {
      id: "2",
      title: "The Last Guardian",
      rating: "PG",
      duration: "1h 55m",
    },
  ];
  
  // Find the movie by ID
  const movie = movies.find(m => m.id === movieId) || movies[0];
  
  // Update the page with movie details
  document.getElementById('movie-title').textContent = movie.title;
  document.getElementById('movie-meta').textContent = `${movie.rating} • ${movie.duration}`;
}

function generateBookingId() {
  // Generate a random booking ID
  return Math.random().toString(36).substring(2, 10).toUpperCase();
}

function formatDate(dateStr) {
  const today = new Date();
  
  if (dateStr === 'today') {
    return today.toLocaleDateString('en-US', { weekday: 'long', month: 'long', day: 'numeric' });
  }
  
  if (dateStr === 'tomorrow') {
    const tomorrow = new Date(today);
    tomorrow.setDate(today.getDate() + 1);
    return tomorrow.toLocaleDateString('en-US', { weekday: 'long', month: 'long', day: 'numeric' });
  }
  
  if (dateStr === 'day-after') {
    const dayAfter = new Date(today);
    dayAfter.setDate(today.getDate() + 2);
    return dayAfter.toLocaleDateString('en-US', { weekday: 'long', month: 'long', day: 'numeric' });
  }
  
  return dateStr;
}