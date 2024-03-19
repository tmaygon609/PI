package com.nttdata.persistence.model;

import java.io.Serializable;
import java.util.Date;

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
 * Clase regsitro lectura.
 * 
 * Representa tabla T_READ_REGISTER.
 */
@Entity
@Table(name = "T_READ_REGISTER")
public class ReadRegister implements Serializable {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/** ID (PK) */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "C_READ_REGISTER_ID")
	private Long id;

	/** Fecha Inicio Lectura */
	@Column(name = "C_START_DATE")
	private Date startDate;

	/** Fecha Fin Lectura */
	@Column(name = "C_END_DATE")
	private Date endDate;

	/** Calificacion */
	@Column(name = "C_RATING")
	private Long rating;

	/** Relación con la entidad Usuario */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "T_USER", referencedColumnName = "C_USER_ID", nullable = true)
	private User user;

	/** Relación con la entidad Libro */
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "T_BOOK", referencedColumnName = "C_BOOK_ID", nullable = true)
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
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
