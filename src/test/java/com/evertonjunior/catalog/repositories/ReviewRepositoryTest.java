package com.evertonjunior.catalog.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import com.evertonjunior.catalog.domain.Movie;
import com.evertonjunior.catalog.domain.Review;
import com.evertonjunior.catalog.domain.User;
import com.evertonjunior.catalog.dto.AuthorDTO;
import com.evertonjunior.catalog.dto.MovieDTO;


@DataMongoTest
class ReviewRepositoryTest {

	@Autowired
	private ReviewRepository repository;
	
	private Review review;
	
	@BeforeEach
	void setupBeforeEach() {
		Movie movie1 = new Movie(null, "Vingadores", 2012, "Acao");
		User user1 = new User(null, "Jose", "jose@gmail.com", "junior098", "Drama", "Terror");
		review = new Review(null, new MovieDTO(movie1), 5.0, new AuthorDTO(user1), "Bom filme");
		repository.save(review);
	}
	@AfterEach
	void setupAfterEach() {
		repository.deleteAll();
	}
	
	@Test
	void testGivenReviewObject_WhenSave_ThenReturnSavedReviewObject() {
		//GIVEN
		//WHEN
		Review savedReview = repository.save(review);
		//THEN
		assertNotNull(savedReview);
		assertEquals("Jose", savedReview.getAuthor().getName());
	}
	
	@Test
	void testGivenReviewObject_WhenFindById_ThenReturnReviewObject() {
		//GIVEN
		//WHEN
		Review foundReview = repository.findById(review.getId()).get();
		//THEN
		assertNotNull(foundReview);
		assertEquals("Jose", foundReview.getAuthor().getName());
	}

	@Test
	void testGivenReviewObject_WhenFindAll_ThenReturnReviewsList() {
		//GIVEN
		User user2 = new User(null, "Maria", "maria@gmail.com", "mariamaria", "Comedia", "Ficcao");
		Movie movie2 = new Movie(null, "De volta ao jogo", 2014, "Acao");
		Review review2 = new Review(null, new MovieDTO(movie2), 4.0, new AuthorDTO(user2),
				"Bom filme, mas nao gostei que matou o cachorrinho");
		repository.save(review2);
		//WHEN
		List<Review> reviews= repository.findAll();
		//THEN
		assertNotNull(reviews);
		assertEquals("Bom filme, mas nao gostei que matou o cachorrinho", reviews.get(1).getComment());
	}
	
	@Test
	void testGivenReviewObject_WhenDeleteById_ThenReturnDeletedReview() {
		//GIVEN
		//WHEN
		repository.deleteById(review.getId());
		Optional <Review> foundReview = repository.findById(review.getId());
		//THEN
		assertTrue(foundReview.isEmpty());
		
	}
}
