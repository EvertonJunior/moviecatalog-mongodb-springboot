package com.evertonjunior.catalog.services;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
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
import com.evertonjunior.catalog.domain.User;
import com.evertonjunior.catalog.repositories.MovieRepository;
import com.evertonjunior.catalog.repositories.UserRepository;
import com.evertonjunior.catalog.services.exceptions.ResourceNotFoundException;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository repository;
	
	@Mock
	private MovieRepository movieRepository;
	
	@InjectMocks
	private UserService service;
	
	private User user;
	
	@BeforeEach
	void setup() {
		user = new User("1", "Jose", "jose@gmail.com", "junior098", "Acao", "Terror");
	}
	
	@Test
	void testGivenUserObject_WhenInsert_ThenReturnInsertUserObject() {
		// GIVEN
		when(repository.save(user)).thenReturn(user);
		// WHEN
		User savedUser = service.insert(user);
		// THEN
		assertEquals("1", savedUser.getId());
		assertEquals("Jose", savedUser.getName());
	}

	@Test
	void testGivenUserObject_WhenFindById_ThenReturnUserObject() {
		// GIVEN
		when(repository.findById(user.getId())).thenReturn(Optional.of(user));
		// WHEN
		User foundUser = service.findById(user.getId());
		// THEN
		assertEquals("1", foundUser.getId());
		assertEquals("Jose", foundUser.getName());
	}
	
	@Test
	void testGivenUserObject_WhenFindById_ThenReturnResoureNotFoundException() {
		// GIVEN
		when(repository.findById(user.getId())).thenThrow(ResourceNotFoundException.class);
		// WHEN
		// THEN
		assertThrows(ResourceNotFoundException.class, () -> service.findById(user.getId()));
	}

	@Test
	void testGivenUserObject_WhenFindAll_ThenReturnUsersList() {
		// GIVEN
		User user1 = new User(null, "Everton", "everton@gmail.com", "everton098", "Acao", "Ficcao");
		when(repository.findAll()).thenReturn(List.of(user, user1));
		// WHEN
		List<User> users = service.findAll();
		// THEN
		assertNotNull(users);
		assertEquals("Jose", users.get(0).getName());
	}

	@Test
	void testGivenUserObject_WhenUpdate_ThenReturnUpdatedUserObject() {
		// GIVEN
		User userUpdated = user;
		userUpdated.setName("Evandro");
		userUpdated.setEmail("evandro@gmail.com");
		when(repository.findById(user.getId())).thenReturn(Optional.of(user));
		when(repository.save(user)).thenReturn(userUpdated);
		// WHEN
		User updatedUser = service.update(user.getId(), userUpdated);
		// THEN
		assertNotNull(updatedUser);
		assertEquals("Evandro", updatedUser.getName());
	}

	@Test
	void testGivenUserObject_WhenDeleteById_ThenReturnDeletedUser() {
        // Given
		when(repository.findById(anyString())).thenReturn(Optional.of(user));
        doNothing().when(repository).deleteById(user.getId());
        
        // When
        assertDoesNotThrow(() -> service.deleteById(user.getId()));
        
        // Then
        verify(repository, times(1)).deleteById(user.getId());
	}
	
	@Test
	void testGivenUserObject_WhenFindByMoviePreference_ThenReturnPreferenceMoviesList() {
		//GIVEN
		Movie movie1 = new Movie(null, "Vingadores", 2012, "Acao");
		Movie movie2 = new Movie(null, "De volta ao jogo", 2014, "Acao");
		Movie movie3 = new Movie(null, "Planeta dos macacos: A origem", 2011, "Ficcao");
		when(repository.findById(user.getId())).thenReturn(Optional.of(user));
		when(movieRepository.findAll()).thenReturn(List.of(movie1, movie2, movie3));
		//WHEN
		List<Movie> movies = service.findByMoviePreference(user.getId());
		//THEN
		assertEquals("Vingadores", movies.get(0).getTitle());
		assertEquals(2, movies.size());
		assertEquals("De volta ao jogo", movies.get(1).getTitle());
	}

}
