package com.evertonjunior.catalog.dto;

import java.io.Serializable;

import com.evertonjunior.catalog.domain.Review;

public class ReviewDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private AuthorDTO author;
	private String comment;
	private Double rating;

	public ReviewDTO() {

	}

	public ReviewDTO(Review review) {
		author = review.getAuthor();
		comment = review.getComment();
		rating = review.getRating();
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

	public Double getRating() {
		return rating;
	}

	public void setRating(Double rating) {
		this.rating = rating;
	}

}
