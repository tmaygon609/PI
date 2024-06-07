package com.nttdata.persistence.model;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa un género.
 * 
 * Representa la tabla T_GENRE en la base de datos.
 */
@Entity
@Table(name = "T_GENRE")
@Getter
@Setter
public class Genre implements Serializable {

	/** Serial version */
	private static final long serialVersionUID = 1L;

	/** ID (PK) */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "C_ID")
	private Long id;

	/** Nombre del género */
	@Column(name = "C_GENRE_NAME")
	private String genreName;

}