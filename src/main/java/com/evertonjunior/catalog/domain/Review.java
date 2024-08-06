package com.evertonjunior.catalog.domain;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.evertonjunior.catalog.dto.AuthorDTO;
import com.evertonjunior.catalog.dto.MovieDTO;

@Document
public class Review implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private MovieDTO movie;
	private Double rating;
	private AuthorDTO author;
	private String comment;

	public Review() {

	}

	public Review(String id, MovieDTO movie, Double rating, AuthorDTO author, String comment) {
		this.id = id;
		this.movie = movie;
		this.rating = rating;
		this.author = author;
		this.comment = comment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MovieDTO getMovie() {
		return movie;
	}

	public void setMovie(MovieDTO movie) {
		this.movie = movie;
	}

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

	public AuthorDTO getAuthor() {
		return author;
	}

	public void setAuthor(AuthorDTO author) {
		this.author = author;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Review other = (Review) obj;
		return Objects.equals(id, other.id);
	}

}
