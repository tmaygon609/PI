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
 * Clase que representa el estado de un libro.
 * 
 * Representa la tabla T_STATUS en la base de datos.
 */
@Entity
@Table(name = "T_STATUS")
@Getter
@Setter
public class Status implements Serializable {

	/** Serial Version */
	private static final long serialVersionUID = 1L;

	/** ID (PK) */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "C_ID")
	private Long id;

	/** Nombre del estado */
	@Column(name = "C_STATUS_NAME")
	private String statusName;

}