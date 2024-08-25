package com.evertonjunior.catalog.resources;

import static org.hamcrest.CoreMatchers.is;
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
import com.evertonjunior.catalog.domain.User;
import com.evertonjunior.catalog.services.MovieService;
import com.evertonjunior.catalog.services.ReviewService;
import com.evertonjunior.catalog.services.UserService;
import com.evertonjunior.catalog.services.exceptions.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest
class UserResourceTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper mapper;

	@MockBean
	private UserService service;
	@MockBean
	private MovieService movieService;
	@MockBean
	private ReviewService reviewService;

	private User user;

	@BeforeEach
	void setup() {
		user = new User("1", "Jose", "jose@gmail.com", "junior098", "Acao", "Terror");
	}

	@Test
	void testGivenUserObject_WhenInsert_ThenReturnInsertUserObject() throws JsonProcessingException, Exception {
		// GIVEN
		when(service.insert(user)).thenReturn(user);
		// WHEN
		ResultActions response = mockMvc.perform(
				post("/users").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(user)));
		// THEN
		response.andExpect(status().isCreated()).andDo(print()).andExpect(jsonPath("$.name", is(user.getName())));
	}

	@Test
	void testGivenUserObject_WhenFindById_ThenReturnUserObject() throws JsonProcessingException, Exception {
		// GIVEN
		when(service.findById(anyString())).thenReturn(user);
		// WHEN
		ResultActions response = mockMvc.perform(get("/users/{id}", user.getId()));;
		// THEN
		response.andExpect(status().isOk());
	}

	@Test
	void testGivenUserObject_WhenFindById_ThenReturnResoureNotFoundException()
			throws JsonProcessingException, Exception {
		// GIVEN
		when(service.findById(user.getId())).thenThrow(ResourceNotFoundException.class);
		// WHEN
		ResultActions response = mockMvc.perform(get("/users/{id}", user.getId()));
		// THEN
		response.andExpect(status().isNotFound()).andDo(print());
	}

	@Test
	void testGivenUserObject_WhenFindAll_ThenReturnUsersList() throws JsonProcessingException, Exception {
		// GIVEN
		User userTwo = new User("2", "Everton", "everton@gmail.com", "everton098", "Acao", "Terror");
		List<User> users = new ArrayList<>(Arrays.asList(user, userTwo));
		when(service.findAll()).thenReturn(List.of(user, userTwo));
		// WHEN
		ResultActions response = mockMvc.perform(get("/users"));
		// THEN
		response.andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(users.size())));
	}

	@Test
	void testGivenUserObject_WhenUpdate_ThenReturnUpdatedUserObject() throws JsonProcessingException, Exception {
		// GIVEN
		User userUpdate = user;
		userUpdate.setName("Eduardo");
		userUpdate.setEmail("eduardo@gmail.com");
		when(service.update(user.getId(), userUpdate)).thenReturn(user);
		// WHEN
		ResultActions response = mockMvc.perform(put("/users/{id}", user.getId())
				.contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(user)));
		// THEN
		response.andExpect(status().isOk()).andDo(print()).andExpect(jsonPath("$.name", is("Eduardo")));
	}

	@Test
	void testGivenUserObject_WhenDeleteById_ThenReturnDeletedUser() throws Exception {
		// Given
		doNothing().when(service).deleteById(user.getId());
		// When
		ResultActions response = mockMvc.perform(delete("/users/{id}", user.getId()));
		// Then
		response.andExpect(status().isNoContent());
	}

	@Test
	void testGivenUserObject_WhenFindByMoviePreference_ThenReturnPreferenceMoviesList() throws Exception {
		// GIVEN
		Movie movie1 = new Movie(null, "Vingadores", 2012, "Acao");
		Movie movie2 = new Movie(null, "De volta ao jogo", 2014, "Acao");
		Movie movie3 = new Movie(null, "Planeta dos macacos: A origem", 2011, "Ficcao");
		when(service.findByMoviePreference(user.getId())).thenReturn(List.of(movie1,movie2));
		// WHEN
		ResultActions response = mockMvc.perform(get("/users/preferences/{id}", user.getId()));
		response.andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.size()", is(2)));
	}
	
	
}
