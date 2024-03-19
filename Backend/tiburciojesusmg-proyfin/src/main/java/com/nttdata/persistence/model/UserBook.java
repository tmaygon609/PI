package com.nttdata.persistence.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * Clase entidad UserBook.
 * 
 * Representa tabla T_USER_BOOK.
 */
@Entity
@Table(name = "T_USER_BOOK")
public class UserBook implements Serializable {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/** ID (PK) */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "C_USER_BOOK_ID")
	private Long id;

	/** Calificacion */
	@Column(name = "C_RATING")
	private Long rating;

	/** Relación con la entidad Usuario */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "C_USER_ID", nullable = true)
	private User user;

	/** Relación con la entidad Libro */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "C_BOOK_ID", nullable = true)
	private Book book;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the rating
	 */
	public Long getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(Long rating) {
		this.rating = rating;
	}

	/**
	 * @return the user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * @return the book
	 */
	public Book getBook() {
		return book;
	}

	/**
	 * @param book the book to set
	 */
	public void setBook(Book book) {
		this.book = book;
	}

}
