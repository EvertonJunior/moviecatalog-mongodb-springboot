package com.evertonjunior.catalog.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.evertonjunior.catalog.domain.Movie;

@DataMongoTest
class MovieRepositoryTest {
	
	@Autowired
	private MovieRepository repository;
	
	private Movie movie;
	
	@BeforeEach
	void setupBeforeEach() {
		movie = new Movie(null, "Vingadores", 2012, "Acao");
		repository.save(movie);
	}
	
	@AfterEach
	void setupAfterEachh() {
		repository.deleteAll();
	}
	
	@Test
	void testGivenMovieObject_WhenSave_ThenReturnSavedMovieObject() {
		//GIVEN
		
		//WHEN
		Movie savedMovie = repository.save(movie);
		//THEN
		assertNotNull(savedMovie);
		assertEquals("Vingadores", savedMovie.getTitle());
	}
	
	@Test
	void testGivenMovieObject_WhenFindById_ThenReturnMovieObject() {
		//GIVEN
		
		//WHEN
		Movie foundMovie = repository.findById(movie.getId()).get();
		//THEN
		assertNotNull(foundMovie);
		assertEquals("Vingadores", foundMovie.getTitle());
	}
	
	@Test
	void testGivenMovieObject_WhenFindByGenreContainingIgnoreCase_ThenReturnMovieObject() {
		//GIVEN
		Movie movie2 = new Movie(null, "De volta ao jogo", 2014, "Acao");
		repository.save(movie2);
		//WHEN
		List<Movie> moviesByGenre = repository.findByGenreContainingIgnoreCase("Acao");
		//THEN
		assertNotNull(moviesByGenre);
		assertEquals(2, moviesByGenre.size());
		assertEquals("De volta ao jogo", moviesByGenre.get(1).getTitle());
	}
	
	@Test
	void testGivenMovieObject_WhenFindAll_ThenReturnMoviesList() {
		//GIVEN
		Movie movie2 = new Movie(null, "De volta ao jogo", 2014, "Acao");
		repository.save(movie2);
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
		//WHEN
		repository.deleteById(movie.getId());
		Optional <Movie> deletedMovie = repository.findById(movie.getId());
		//THEN
		assertTrue(deletedMovie.isEmpty());
	}
}
