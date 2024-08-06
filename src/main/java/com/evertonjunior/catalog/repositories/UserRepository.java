package com.evertonjunior.catalog.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.evertonjunior.catalog.domain.User;

public interface UserRepository extends MongoRepository<User, String> {

}
