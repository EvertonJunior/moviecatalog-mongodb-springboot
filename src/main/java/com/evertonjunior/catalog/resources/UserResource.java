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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.evertonjunior.catalog.domain.Movie;
import com.evertonjunior.catalog.domain.User;
import com.evertonjunior.catalog.dto.UserDTO;
import com.evertonjunior.catalog.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {

	@Autowired
	private UserService service;

	@GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> users = service.findAll();
		List<UserDTO> userDto = users.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());
		return ResponseEntity.ok().body(userDto);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		UserDTO userDto = new UserDTO(service.findById(id));
		return ResponseEntity.ok().body(userDto);
	}

	@PostMapping
	public ResponseEntity<User> insert(@RequestBody User user) {
		user = service.insert(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).body(user);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<User> update(@PathVariable String id, @RequestBody User user) {
		service.update(id, user);
		return ResponseEntity.ok().body(user);
	}

	@GetMapping(value = "/preferences/{id}")
	public ResponseEntity<List<Movie>> findByMoviePreference(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findByMoviePreference(id));
	}

}
