package com.evertonjunior.catalog.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evertonjunior.catalog.domain.Movie;
import com.evertonjunior.catalog.domain.User;
import com.evertonjunior.catalog.repositories.MovieRepository;
import com.evertonjunior.catalog.repositories.UserRepository;
import com.evertonjunior.catalog.services.exceptions.ResourceNotFoundException;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private MovieRepository movieRepository;

	public List<User> findAll() {
		return repository.findAll();
	}

	public User findById(String id) {
		Optional<User> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public User insert(User user) {
		return repository.insert(user);
	}

	public void deleteById(String id) {
		findById(id);
		repository.deleteById(id);
	}

	public User update(String id, User user) {
		User entity = findById(id);
		updateData(entity, user);
		return repository.save(entity);
	}

	public void updateData(User entity, User user) {
		entity.setName(user.getName());
		entity.setEmail(user.getEmail());
	}

	public List<Movie> findByMoviePreference(String id) {
		List<String> preferences = findById(id).getPreferences();
		List<Movie> movies = movieRepository.findAll().stream().filter(filme -> preferences.contains(filme.getGenre()))
				.collect(Collectors.toList());
		return movies;

	}

}
