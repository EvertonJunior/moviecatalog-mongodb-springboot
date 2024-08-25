package com.evertonjunior.catalog.resources;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.evertonjunior.catalog.domain.Movie;
import com.evertonjunior.catalog.domain.Review;
import com.evertonjunior.catalog.domain.User;
import com.evertonjunior.catalog.dto.AuthorDTO;
import com.evertonjunior.catalog.dto.MovieDTO;
import com.evertonjunior.catalog.services.MovieService;
import com.evertonjunior.catalog.services.ReviewService;
import com.evertonjunior.catalog.services.UserService;
import com.evertonjunior.catalog.services.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
class ReviewResourceTest {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserService userService;
	@MockBean
	private MovieService movieService;
	@MockBean
	private ReviewService service;

	private Review review;

	@BeforeEach
	void setup() {
		Movie movie1 = new Movie("1", "Vingadores", 2012, "Acao");
		User user1 = new User("1", "Jose", "jose@gmail.com", "junior098", "Drama", "Terror");
		review = new Review("1", new MovieDTO(movie1), 5.0, new AuthorDTO(user1), "Bom filme");
	}

	@Test
	void testGivenReviewObject_WhenInsert_ThenReturnSavedReviewObject() throws JsonProcessingException, Exception {
		// GIVEN
		when(service.insert(any(Review.class))).thenReturn(review);
		// WHEN
		ResultActions response = mockMvc.perform(
				post("/reviews").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(review)));
		// THEN
		response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.rating", is(review.getRating())));
	}

	@Test
	void testGivenReviewObject_WhenFindById_ThenReturnReviewObject() throws Exception {
		// GIVEN
		when(service.findById(review.getId())).thenReturn(review);
		// WHEN
		ResultActions response = mockMvc.perform(get("/reviews/{id}", review.getId()));
		// THEN
		response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.rating", is(review.getRating())));
	}

	@Test
	void testGivenReviewObject_WhenFindById_ThenReturnResourceNotFoundException() throws Exception {
		// GIVEN
		when(service.findById(anyString())).thenThrow(ResourceNotFoundException.class);
		// WHEN
		ResultActions response = mockMvc.perform(get("/reviews/{id}", review.getId()));
		// THEN
		response.andExpect(status().isNotFound());
	}

	@Test
	void testGivenReviewObject_WhenFindAll_ThenReturnReviewsList() throws Exception {
		// GIVEN
		User user2 = new User(null, "Maria", "maria@gmail.com", "mariamaria", "Comedia", "Ficcao");
		Movie movie2 = new Movie(null, "De volta ao jogo", 2014, "Acao");
		Review review2 = new Review(null, new MovieDTO(movie2), 4.0, new AuthorDTO(user2),
				"Bom filme, mas nao gostei que matou o cachorrinho");
		List<Review> reviews = new ArrayList<>(Arrays.asList(review, review2));
		when(service.findAll()).thenReturn(reviews);
		// WHEN
		ResultActions response = mockMvc.perform(get("/reviews"));
		// THEN
		response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(reviews.size())))
				.andExpect(jsonPath("$.[1]comment", is(review2.getComment())));
	}

	@Test
	void testReviewObject_WhenUpdate_ThenReturnUpdatedReviewObject() throws JsonProcessingException, Exception {
		// GIVEN
		Review updateReview = review;
		updateReview.setRating(4.7);
		updateReview.setComment("Filme razoavel");
		when(service.update(review.getId(), updateReview)).thenReturn(review);
		// WHEN
		ResultActions response = mockMvc.perform(put("/reviews/{id}", review.getId())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(review)));
		// THEN
		response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.rating", is(4.7)))
				.andExpect(jsonPath("$.comment", is("Filme razoavel")));
	}

	@Test
	void testGivenReviewObject_WhenDeleteById_ThenReturnDeletedReview() throws Exception {
		// GIVEN
		doNothing().when(service).deleteById(review.getId());
		// WHEN
		ResultActions response = mockMvc.perform(delete("/reviews/{id}", review.getId()));
		// THEN
		response.andExpect(status().isNoContent());
	}
}
