package com.nttdata.persistence.model;

import java.io.Serializable;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * Clase libro.
 * 
 * Representa tabla T_BOOK.
 */
@Entity
@Table(name = "T_BOOK")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Book implements Serializable {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/** ID (PK) */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "C_BOOK_ID")
	private Long id;

	/** Nombre */
	@Column(name = "C_TITLE")
	private String title;

	/** Autor */
	@Column(name = "C_AUTHOR")
	private String author;

	/** Genero */
	@Column(name = "C_GENRE")
	private String genre;

	@OneToMany(mappedBy = "book")
	@JsonProperty(access = Access.WRITE_ONLY)
	private Set<UserBook> userBooks;

//	@ManyToMany(mappedBy = "books")
//	@JsonProperty(access = Access.WRITE_ONLY)
//	private Set<User> users;

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
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the author
	 */
	public String getAuthor() {
		return author;
	}

	/**
	 * @param author the author to set
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	/**
	 * @return the genre
	 */
	public String getGenre() {
		return genre;
	}

	/**
	 * @param genre the genre to set
	 */
	public void setGenre(String genre) {
		this.genre = genre;
	}

	/**
	 * @return the userBooks
	 */
	public Set<UserBook> getUserBooks() {
		return userBooks;
	}

	/**
	 * @param userBooks the userBooks to set
	 */
	public void setUserBooks(Set<UserBook> userBooks) {
		this.userBooks = userBooks;
	}

//	/**
//	 * @return the users
//	 */
//	public Set<User> getUsers() {
//		return users;
//	}
//
//	/**
//	 * @param users the users to set
//	 */
//	public void setUsers(Set<User> users) {
//		this.users = users;
//	}

}
