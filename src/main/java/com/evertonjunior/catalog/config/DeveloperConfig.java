package com.evertonjunior.catalog.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.evertonjunior.catalog.domain.Movie;
import com.evertonjunior.catalog.domain.Review;
import com.evertonjunior.catalog.domain.User;
import com.evertonjunior.catalog.dto.AuthorDTO;
import com.evertonjunior.catalog.dto.MovieDTO;
import com.evertonjunior.catalog.dto.ReviewDTO;
import com.evertonjunior.catalog.repositories.MovieRepository;
import com.evertonjunior.catalog.repositories.ReviewRepository;
import com.evertonjunior.catalog.repositories.UserRepository;

@Configuration
public class DeveloperConfig implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private MovieRepository movieRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Override
	public void run(String... args) throws Exception {

		userRepository.deleteAll();
		movieRepository.deleteAll();
		reviewRepository.deleteAll();

		User user1 = new User(null, "Jose", "jose@gmail.com", "junior098", "Drama", "Terror");
		User user2 = new User(null, "Maria", "maria@gmail.com", "mariamaria", "Comedia", "Ficcao");
		User user3 = new User(null, "Julieta", "julieta@gmail.com", "julieta12334", "Terror", "Comedia");

		userRepository.saveAll(Arrays.asList(user1, user2, user3));

		Movie movie1 = new Movie(null, "Vingadores", 2012, "Acao");
		Movie movie2 = new Movie(null, "De volta ao jogo", 2014, "Acao");
		Movie movie3 = new Movie(null, "Planeta dos macacos: A origem", 2011, "Ficcao");

		movieRepository.saveAll(Arrays.asList(movie1, movie2, movie3));

		Review review1 = new Review(null, new MovieDTO(movie1), 5.0, new AuthorDTO(user2), "Bom filme");
		Review review2 = new Review(null, new MovieDTO(movie1), 4.5, new AuthorDTO(user3),
				"Bom filme, porem muita demora para desenrolar a historia");
		Review review3 = new Review(null, new MovieDTO(movie2), 4.0, new AuthorDTO(user2),
				"Bom filme, mas nao gostei que matou o cachorrinho");
		Review review4 = new Review(null, new MovieDTO(movie2), 5.0, new AuthorDTO(user3),
				"Filme muito bom, acao o tempo todo");
		Review review5 = new Review(null, new MovieDTO(movie3), 5.0, new AuthorDTO(user1),
				"Otimo filme, so tenho medo que um dia isso aconteca de verdade");
		Review review6 = new Review(null, new MovieDTO(movie3), 2.0, new AuthorDTO(user3), "Nao gostei");
		Review review7 = new Review(null, new MovieDTO(movie1), 5.0, new AuthorDTO(user1), "Adoro o capitao america");

		reviewRepository.saveAll(Arrays.asList(review1, review2, review3, review4, review5, review6, review7));

		movie1.getReviews()
				.addAll(Arrays.asList(new ReviewDTO(review1), new ReviewDTO(review2), new ReviewDTO(review3)));
		movie2.getReviews().addAll(Arrays.asList(new ReviewDTO(review3), new ReviewDTO(review4)));
		movie3.getReviews().addAll(Arrays.asList(new ReviewDTO(review5), new ReviewDTO(review6)));
		movie1.setAverageRating(movie1.averageRatingCalculator());
		movie2.setAverageRating(movie2.averageRatingCalculator());
		movie3.setAverageRating(movie3.averageRatingCalculator());

		user1.getReviews().addAll(Arrays.asList(review5, review7));
		user2.getReviews().addAll(Arrays.asList(review1, review3));
		user3.getReviews().addAll(Arrays.asList(review2, review4, review6));

		userRepository.saveAll(Arrays.asList(user1, user2, user3));
		movieRepository.saveAll(Arrays.asList(movie1, movie2, movie3));

	}

}
