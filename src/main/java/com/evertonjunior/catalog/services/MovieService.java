package com.evertonjunior.catalog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evertonjunior.catalog.domain.Movie;
import com.evertonjunior.catalog.repositories.MovieRepository;
import com.evertonjunior.catalog.services.exceptions.ResourceNotFoundException;

@Service
public class MovieService {

	@Autowired
	private MovieRepository repository;

	public List<Movie> findAll() {
		return repository.findAll();
	}

	public Movie findById(String id) {
		Optional<Movie> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Movie insert(Movie movie) {
		return repository.save(movie);
	}

	public void deleteById(String id) {
		findById(id);
		repository.deleteById(id);
	}

	public List<Movie> findByGenre(String genre) {
		return repository.findByGenreContainingIgnoreCase(genre);
	}

}
