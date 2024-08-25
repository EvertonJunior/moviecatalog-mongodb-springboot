package com.evertonjunior.catalog.resources;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
import com.evertonjunior.catalog.services.MovieService;
import com.evertonjunior.catalog.services.ReviewService;
import com.evertonjunior.catalog.services.UserService;
import com.evertonjunior.catalog.services.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
class MovieResourceTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private MovieService service;
	@MockBean
	private UserService userService;
	@MockBean
	private ReviewService reviewService;

	private Movie movie;

	@BeforeEach
	void setup() {
		movie = new Movie("1L", "Vingadores", 2012, "Acao");
	}

	@Test
	void testGivenMovieObject_WhenInsert_ThenReturnInsertMovieObject() throws JsonProcessingException, Exception {
		// GIVEN
		when(service.insert(movie)).thenReturn(movie);
		// WHEN
		ResultActions response = mockMvc.perform(
				post("/movies").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(movie)));
		// THEN
		response.andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.title", is(movie.getTitle())));
	}

	@Test
	void testGivenMovieObject_WhenFindById_ThenReturnMovieObject() throws Exception {
		// GIVEN
		when(service.findById(movie.getId())).thenReturn(movie);
		// WHEN
		ResultActions response = mockMvc.perform(get("/movies/{id}", movie.getId()));
		// THEN
		response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.title", is("Vingadores")))
				.andExpect(jsonPath("$.title", is(movie.getTitle())));
	}

	@Test
	void testGivenMovieObject_WhenFindByGenre_ThenReturnMovieObject() throws Exception {
		// GIVEN
		Movie movie2 = new Movie(null, "De volta ao jogo", 2014, "Acao");
		List<Movie> movies = new ArrayList<>(Arrays.asList(movie, movie2));
		when(service.findByGenre("Acao")).thenReturn(movies);
		// WHEN
		ResultActions response = mockMvc.perform(get("/movies/genre/{genre}", "Acao"));
		// THEN
		response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(2)))
				.andExpect(jsonPath("$.[1]title", is(movies.get(1).getTitle())));
	}

	@Test
	void testGivenMovieObject_WhenFindById_ThenReturnNotFoundException() throws Exception {
		// GIVEN
		when(service.findById("4")).thenThrow(ResourceNotFoundException.class);
		// WHEN
		ResultActions response = mockMvc.perform(get("/movies/{id}", "4"));
		// THEN
		response.andExpect(status().isNotFound());
	}

	@Test
	void testGivenMovieObject_WhenFindAll_ThenReturnMoviesList() throws Exception {
		// GIVEN
		Movie movie2 = new Movie(null, "De volta ao jogo", 2014, "Acao");
		List<Movie> movies = new ArrayList<>(Arrays.asList(movie, movie2));
		when(service.findAll()).thenReturn(movies);
		// WHEN
		ResultActions response = mockMvc.perform(get("/movies"));
		// THEN
		response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(2)))
		.andExpect(jsonPath("$.[1]title", is(movies.get(1).getTitle())));
	}

	@Test
	void testGivenMovieObject_WhenDeleteById_ThenReturnDeletedMovie() throws Exception {
		// GIVEN
		doNothing().when(service).deleteById(movie.getId());
		// WHEN
		ResultActions response = mockMvc.perform(delete("/movies/{id}", movie.getId()));
		// THEN
		response.andExpect(status().isNoContent());
	}

}
