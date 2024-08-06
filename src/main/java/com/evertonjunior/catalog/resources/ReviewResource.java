package com.evertonjunior.catalog.resources;

import java.net.URI;
import java.util.List;

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

import com.evertonjunior.catalog.domain.Review;
import com.evertonjunior.catalog.services.ReviewService;

@RestController
@RequestMapping(value = "/reviews")
public class ReviewResource {

	@Autowired
	private ReviewService service;

	@GetMapping
	public ResponseEntity<List<Review>> findAll() {
		List<Review> reviews = service.findAll();
		return ResponseEntity.ok().body(reviews);
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Review> findById(@PathVariable String id) {
		return ResponseEntity.ok().body(service.findById(id));
	}

	@PostMapping
	public ResponseEntity<Review> insert(Review review) {
		review = service.insert(review);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(review.getId()).toUri();
		return ResponseEntity.created(uri).body(review);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		service.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@PutMapping(value = "/{id}")
	public ResponseEntity<Review> update(@PathVariable String id, @RequestBody Review review) {
		service.update(id, review);
		return ResponseEntity.ok().body(review);
	}

}
