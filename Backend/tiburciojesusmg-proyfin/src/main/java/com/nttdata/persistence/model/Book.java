package com.nttdata.persistence.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * Clase persona.
 * 
 * Representa tabla T_BOOK.
 */
@Entity
@Table(name = "T_BOOK")
public class Book implements Serializable{
	
	/** Serial Version */
	private static final long serialVersionUID = 1L;
	
	/** ID (PK) */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "C_PERSON_ID")
	private Long id;
	
	/** Nombre */
	@Column(name = "C_TITLE")
	private String title;
	
	/** Autor */
	@Column(name = "C_AUTHOR")
	private String author;
	
//	/** Documento de identidad */
//	@Column(name = "C_IDENTITY_DOC", unique = true, nullable = false)
//	private String identityDoc;
//	
	

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

	
	

}
