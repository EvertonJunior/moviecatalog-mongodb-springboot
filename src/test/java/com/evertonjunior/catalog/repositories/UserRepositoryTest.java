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

import com.evertonjunior.catalog.domain.User;

@DataMongoTest
class UserRepositoryTest {

	@Autowired
	private UserRepository repository;
	
	private User user;
	
	@BeforeEach
	void setupBeforeEach() {
		user = new User(null, "Jose", "jose@gmail.com", "junior098", "Drama", "Terror");
		repository.save(user);
	}
	
	@AfterEach
	void setupAfterEach() {
		repository.deleteAll();
	}

	@Test
	void testGivenUserObject_WhenSave_ThenReturnSavedUserObject() {
		//GIVEN
		//WHEN
		User savedUser = repository.save(user);
		//THEN
		assertNotNull(savedUser);
		assertEquals("Jose", savedUser.getName());
	}

	@Test
	void testGivenUserObject_WhenFindById_ThenReturnUserObject() {
		//GIVEN
		//WHEN
		User foundUser = repository.findById(user.getId()).get();
		//THEN
		assertNotNull(foundUser);
		assertEquals("Jose", foundUser.getName());
	}
	
	@Test
	void testGivenUserObject_WhenFindAll_ThenReturnUsersList() {
		//GIVEN
		User user1 = new User(null, "Everton", "everton@gmail.com", "everton098", "Comedia", "Drama");
		repository.save(user1);
		//WHEN
		List<User> users = repository.findAll();
		//THEN
		assertNotNull(users);
		assertEquals(2, users.size());
		assertEquals("Everton", users.get(1).getName());
	}
	
	@Test
	void testGivenUserObject_WhenDeleteById_ThenReturnDeletedUser() {
		//GIVEN
		//WHEN
		repository.deleteById(user.getId());
		Optional<User> deletedUser = repository.findById(user.getId());
		//THEN
		assertTrue(deletedUser.isEmpty());
	}

}
