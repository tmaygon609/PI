package com.nttdata.persistence.model;

import java.io.Serializable;
import java.util.Set;

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

	/** Relación con la entidad Registro de Lectura */
	@OneToMany(mappedBy = "book")
	private Set<ReadRegister> readRegisters;

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
	 * @return the readRegisters
	 */
	public Set<ReadRegister> getReadRegisters() {
		return readRegisters;
	}

	/**
	 * @param readRegisters the readRegisters to set
	 */
	public void setReadRegisters(Set<ReadRegister> readRegisters) {
		this.readRegisters = readRegisters;
	}

}
