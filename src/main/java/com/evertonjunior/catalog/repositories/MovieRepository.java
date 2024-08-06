package com.evertonjunior.catalog.repositories;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.evertonjunior.catalog.domain.Movie;

public interface MovieRepository extends MongoRepository<Movie, String> {

	public List<Movie> findByGenreContainingIgnoreCase(String genre);
}
