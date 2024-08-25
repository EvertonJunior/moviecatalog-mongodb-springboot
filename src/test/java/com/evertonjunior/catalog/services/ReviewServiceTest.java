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
import com.evertonjunior.catalog.domain.Review;
import com.evertonjunior.catalog.domain.User;
import com.evertonjunior.catalog.dto.AuthorDTO;
import com.evertonjunior.catalog.dto.MovieDTO;
import com.evertonjunior.catalog.repositories.ReviewRepository;
import com.evertonjunior.catalog.services.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {
	
	@Mock
	private ReviewRepository repository;
	@InjectMocks
	private ReviewService service;

	private Review review;
	
	@BeforeEach
	void setup() {
		Movie movie1 = new Movie("1", "Vingadores", 2012, "Acao");
		User user1 = new User("1", "Jose", "jose@gmail.com", "junior098", "Drama", "Terror");
		review = new Review("1", new MovieDTO(movie1), 5.0, new AuthorDTO(user1), "Bom filme");
	}
	
	@Test
	void testGivenReviewObject_WhenSave_ThenReturnSavedReviewObject() {
		//GIVEN
		when(repository.save(review)).thenReturn(review);
		//WHEN
		Review insertReview = service.insert(review);
		//THEN
		assertNotNull(insertReview);
		assertEquals(5.0, insertReview.getRating());
	}
	
	@Test
	void testGivenReviewObject_WhenFindById_ThenReturnReviewObject() {
		//GIVEN
		when(repository.findById(review.getId())).thenReturn(Optional.of(review));
		//WHEN
		Review foundReview = service.findById(review.getId());
		//THEN
		assertNotNull(foundReview);
		assertEquals(5.0, foundReview.getRating());
	}
	
	@Test
	void testGivenReviewObject_WhenFindById_ThenReturnResourceNotFoundException() {
		//GIVEN
		when(repository.findById("4")).thenThrow(ResourceNotFoundException.class);
		//WHEN
		//THEN
		assertThrows(ResourceNotFoundException.class, () -> service.findById("4"));
	}

	@Test
	void testGivenReviewObject_WhenFindAll_ThenReturnReviewsList() {
		//GIVEN
		User user2 = new User(null, "Maria", "maria@gmail.com", "mariamaria", "Comedia", "Ficcao");
		Movie movie2 = new Movie(null, "De volta ao jogo", 2014, "Acao");
		Review review2 = new Review(null, new MovieDTO(movie2), 4.0, new AuthorDTO(user2),
				"Bom filme, mas nao gostei que matou o cachorrinho");
		when(repository.findAll()).thenReturn(List.of(review,review2));
		//WHEN
		List<Review> reviews = service.findAll();
		//THEN
		assertNotNull(reviews);
		assertEquals(2, reviews.size());
		assertEquals("Jose", reviews.get(0).getAuthor().getName());
	}
	
	@Test
	void testReviewObject_WhenUpdate_ThenReturnUpdatedReviewObject() {
		//GIVEN
		Review updateReview = review;
		updateReview.setRating(4.7);
		updateReview.setComment("Filme razoavel");
		when(repository.findById(review.getId())).thenReturn(Optional.of(review));
		when(repository.save(review)).thenReturn(review);
		//WHEN
		Review updatedReview = service.update(review.getId(), updateReview);
		//THEN
		assertNotNull(updatedReview);
		assertEquals(4.7, updatedReview.getRating());
		assertEquals("Filme razoavel", updatedReview.getComment());
	}
	
	@Test
	void testGivenReviewObject_WhenDeleteById_ThenReturnDeletedReview() {
		//GIVEN
		when(repository.findById(review.getId())).thenReturn(Optional.of(review));
		doNothing().when(repository).deleteById(review.getId());
		//WHEN
		service.deleteById(review.getId());
		//THEN
		verify(repository, times(1)).deleteById(review.getId());
	}
	
	
}
