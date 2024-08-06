package com.evertonjunior.catalog.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.evertonjunior.catalog.domain.Movie;
import com.evertonjunior.catalog.dto.MovieDTO;
import com.evertonjunior.catalog.resources.util.URL;
import com.evertonjunior.catalog.services.MovieService;

@RestController
@RequestMapping(value = "/movies")
public class MovieResource {

	@Autowired
	private MovieService service;

	@GetMapping
	public ResponseEntity<List<Movie>> findAll() {
		return ResponseEntity.ok().body(service.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Movie> findById(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<Movie> insert(@RequestBody Movie movie) {
		movie = service.insert(movie);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(movie.getId()).toUri();
		return ResponseEntity.created(uri).body(movie);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/genresearch")
	public ResponseEntity<List<MovieDTO>> findByGenre(@RequestParam(value = ("text"), defaultValue = "") String genre) {
		genre = URL.decodeParam(genre);
		List<MovieDTO> movieDto = service.findByGenre(genre).stream().map(x -> new MovieDTO(x))
				.collect(Collectors.toList());
		return ResponseEntity.ok().body(movieDto);
	}
}
