package com.evertonjunior.catalog.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.evertonjunior.catalog.domain.Review;
import com.evertonjunior.catalog.repositories.ReviewRepository;
import com.evertonjunior.catalog.services.exceptions.ResourceNotFoundException;

@Service
public class ReviewService {

	@Autowired
	private ReviewRepository repository;

	public List<Review> findAll() {
		return repository.findAll();
	}

	public Review findById(String id) {
		Optional<Review> obj = repository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Review insert(Review review) {
		return repository.insert(review);
	}

	public void deleteById(String id) {
		findById(id);
		repository.deleteById(id);
	}

	public Review update(String id, Review review) {
		Review entity = findById(id);
		updateData(entity, review);
		return repository.save(entity);
	}

	public void updateData(Review entity, Review review) {
		entity.setRating(review.getRating());
		entity.setComment(review.getComment());
	}
}
