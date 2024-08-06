package com.evertonjunior.catalog.dto;

import java.io.Serializable;

import com.evertonjunior.catalog.domain.Movie;

public class MovieDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	private String title;

	public MovieDTO() {

	}

	public MovieDTO(Movie movie) {
		id = movie.getId();
		title = movie.getTitle();
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

}
