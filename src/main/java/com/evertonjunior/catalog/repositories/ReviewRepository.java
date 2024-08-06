package com.evertonjunior.catalog.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.evertonjunior.catalog.domain.Review;

public interface ReviewRepository extends MongoRepository<Review, String> {

}
