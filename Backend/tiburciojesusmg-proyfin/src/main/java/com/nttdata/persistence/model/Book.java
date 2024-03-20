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
import lombok.Getter;
import lombok.Setter;

/**
 * Clase libro.
 * 
 * Representa tabla T_BOOK.
 */
@Entity
@Table(name = "T_BOOK")
@Getter
@Setter
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

}
