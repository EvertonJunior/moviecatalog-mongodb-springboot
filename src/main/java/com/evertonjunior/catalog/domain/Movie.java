package com.evertonjunior.catalog.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.evertonjunior.catalog.dto.ReviewDTO;

@Document
public class Movie implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;
	private String title;
	private Integer year;
	private String genre;

	private Double averageRating;

	private List<ReviewDTO> reviews = new ArrayList<>();

	public Movie() {

	}

	public Movie(String id, String title, Integer year, String genre) {
		this.id = id;
		this.title = title;
		this.year = year;
		this.genre = genre;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(Double averageRating) {
		this.averageRating = averageRating;
	}

	public List<ReviewDTO> getReviews() {
		return reviews;
	}

	public Double averageRatingCalculator() {
		Double total = 0.0;
		for (ReviewDTO x : reviews) {
			total += x.getRating();
		}
		return total / reviews.size();
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
		Movie other = (Movie) obj;
		return Objects.equals(id, other.id);
	}

}
