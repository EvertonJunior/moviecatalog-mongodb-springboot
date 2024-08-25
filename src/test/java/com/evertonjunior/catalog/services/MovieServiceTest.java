package com.evertonjunior.catalog.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.evertonjunior.catalog.domain.Movie;
import com.evertonjunior.catalog.repositories.MovieRepository;
import com.evertonjunior.catalog.services.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class MovieServiceTest {

	@Mock
	private MovieRepository repository;
	
	@InjectMocks
	private MovieService service;
	
	private Movie movie;

	@BeforeEach
	void setup() {
		movie = new Movie(null, "Vingadores", 2012, "Acao");
	}
	
	@Test
	void testGivenMovieObject_WhenInsert_ThenReturnInsertMovieObject() {
		//GIVEN
		when(repository.save(movie)).thenReturn(movie);
		//WHEN
		Movie insertMovie = service.insert(movie);
		//THEN
		assertNotNull(insertMovie);
		assertEquals("Vingadores", insertMovie.getTitle());
	}
	
	@Test
	void testGivenMovieObject_WhenFindById_ThenReturnMovieObject() {
		//GIVEN
		when(repository.findById(movie.getId())).thenReturn(Optional.of(movie));
		//WHEN
		Movie foundMovie = service.findById(movie.getId());
		//THEN
		assertNotNull(foundMovie);
		assertEquals("Vingadores", foundMovie.getTitle());
	}
	
	@Test
	void testGivenMovieObject_WhenFindByGenre_ThenReturnMovieObject() {
		//GIVEN
		Movie movie2 = new Movie(null, "De volta ao jogo", 2014, "Acao");
		when(repository.findByGenreContainingIgnoreCase("Acao")).thenReturn(List.of(movie, movie2));
		//WHEN
		List<Movie> foundMovies = service.findByGenre("Acao");
		//THEN
		assertNotNull(foundMovies);
		assertEquals(2, foundMovies.size());
		assertEquals("Vingadores", foundMovies.get(0).getTitle());
	}
	
	@Test
	void testGivenMovieObject_WhenFindById_ThenReturnNotFoundException() {
		//GIVEN
		when(repository.findById("9")).thenThrow(ResourceNotFoundException.class);
		//WHEN
		//THEN
		assertThrows(ResourceNotFoundException.class, () -> service.findById("9"));
	}
	
	@Test
	void testGivenMovieObject_WhenFindAll_ThenReturnMoviesList() {
		//GIVEN
		Movie movie2 = new Movie(null, "De volta ao jogo", 2014, "Acao");
		when(repository.findAll()).thenReturn(List.of(movie,movie2));
		//WHEN
		List<Movie> movies = repository.findAll();
		//THEN
		assertNotNull(movies);
		assertEquals(2, movies.size());
		assertEquals("De volta ao jogo", movies.get(1).getTitle());
	}

	@Test
	void testGivenMovieObject_WhenDeleteById_ThenReturnDeletedMovie() {
		//GIVEN
		when(repository.findById(movie.getId())).thenReturn(Optional.of(movie));
		doNothing().when(repository).deleteById(movie.getId());
		//WHEN
		service.deleteById(movie.getId());
		//THEN
		verify(repository, times(1)).deleteById(movie.getId());
	}
}
