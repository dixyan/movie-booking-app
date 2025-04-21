document.addEventListener('DOMContentLoaded', function() {
  // Initialize Feather icons
  feather.replace();
  
  // Tab functionality
  const tabTriggers = document.querySelectorAll('.tab-trigger');
  tabTriggers.forEach(trigger => {
    trigger.addEventListener('click', () => {
      const tabId = trigger.getAttribute('data-tab');
      
      // Update active tab trigger
      document.querySelectorAll('.tab-trigger').forEach(t => {
        t.classList.remove('active');
      });
      trigger.classList.add('active');
      
      // Show active tab content
      document.querySelectorAll('.tab-content').forEach(content => {
        content.classList.remove('active');
      });
      document.getElementById(tabId).classList.add('active');
    });
  });
  
  // Load movie data
  loadMovies();
});

// Declare feather variable
const feather = window.feather;

function loadMovies() {
  const nowShowingMovies = [
    {
      id: "1",
      title: "Interstellar: Beyond Time",
      posterUrl: "https://via.placeholder.com/300x450",
      rating: "PG-13",
      duration: "2h 45m",
      genre: "Sci-Fi/Adventure",
    },
    {
      id: "2",
      title: "The Last Guardian",
      posterUrl: "https://via.placeholder.com/300x450",
      rating: "PG",
      duration: "1h 55m",
      genre: "Fantasy/Adventure",
    },
    {
      id: "3",
      title: "Midnight in Paris",
      posterUrl: "https://via.placeholder.com/300x450",
      rating: "R",
      duration: "2h 10m",
      genre: "Romance/Drama",
    },
    {
      id: "4",
      title: "The Silent Echo",
      posterUrl: "https://via.placeholder.com/300x450",
      rating: "PG-13",
      duration: "2h 5m",
      genre: "Thriller/Mystery",
    },
    {
      id: "5",
      title: "Legends of Tomorrow",
      posterUrl: "https://via.placeholder.com/300x450",
      rating: "PG-13",
      duration: "2h 30m",
      genre: "Action/Adventure",
    },
    {
      id: "6",
      title: "The Forgotten Path",
      posterUrl: "https://via.placeholder.com/300x450",
      rating: "R",
      duration: "1h 50m",
      genre: "Horror/Thriller",
    },
  ];

  const comingSoonMovies = [
    {
      id: "7",
      title: "Eternal Sunshine",
      posterUrl: "https://via.placeholder.com/300x450",
      duration: "2h 15m",
      genre: "Drama/Romance",
    },
    {
      id: "8",
      title: "The Dark Horizon",
      posterUrl: "https://via.placeholder.com/300x450",
      duration: "2h 25m",
      genre: "Action/Thriller",
    },
    {
      id: "9",
      title: "Whispers in the Wind",
      posterUrl: "https://via.placeholder.com/300x450",
      duration: "1h 45m",
      genre: "Mystery/Drama",
    },
  ];

  // Render now showing movies
  const nowShowingGrid = document.querySelector('#now-showing .movie-grid');
  nowShowingMovies.forEach(movie => {
    const movieCard = createMovieCard(movie);
    nowShowingGrid.appendChild(movieCard);
  });

  // Render coming soon movies
  const comingSoonGrid = document.querySelector('#coming-soon .movie-grid');
  comingSoonMovies.forEach(movie => {
    const movieCard = createMovieCard(movie, true);
    comingSoonGrid.appendChild(movieCard);
  });
}

function createMovieCard(movie, comingSoon = false) {
  const template = document.getElementById('movie-card-template');
  const movieCard = template.content.cloneNode(true);
  
  // Set movie data
  movieCard.querySelector('.movie-title').textContent = movie.title;
  movieCard.querySelector('.movie-poster img').src = movie.posterUrl;
  movieCard.querySelector('.movie-poster img').alt = movie.title;
  movieCard.querySelector('.movie-duration').textContent = movie.duration;
  movieCard.querySelector('.movie-genre').textContent = movie.genre;
  
  // Set rating if available
  if (movie.rating) {
    movieCard.querySelector('.movie-rating').textContent = movie.rating;
  } else {
    movieCard.querySelector('.movie-rating').remove();
  }
  
  // Set button text and link
  const bookBtn = movieCard.querySelector('.book-btn');
  if (comingSoon) {
    bookBtn.textContent = 'Coming Soon';
    bookBtn.classList.remove('btn-primary');
    bookBtn.classList.add('btn-outline');
    bookBtn.disabled = true;
  } else {
    bookBtn.textContent = 'Book Tickets';
    bookBtn.href = `movie-details.html?id=${movie.id}`;
  }
  
  return movieCard;
}