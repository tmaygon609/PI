package com.nttdata.persistence.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa la relación entre un usuario y un libro.
 * 
 * Representa la tabla T_USER_BOOK en la base de datos.
 */
@Entity
@Table(name = "T_USER_BOOK")
@Getter
@Setter
public class UserBook implements Serializable {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/** ID (PK) */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "C_ID")
	private Long id;

	/** Usuario asociado */
	@ManyToOne
	@JoinColumn(name = "C_USER_ID")
	private User user;

	/** Libro asociado */
	@ManyToOne
	@JoinColumn(name = "C_BOOK_ID")
	private Book book;

	/** Estado del libro para el usuario */
	@Column(name = "C_STATUS")
	private String status;

	/** Calificación del libro */
	@Column(name = "C_RATE")
	private String rate;

	/** Comentario del usuario sobre el libro */
	@Column(name = "C_COMMENT")
	private String comment;

}