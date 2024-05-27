package com.nttdata.persistence.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object para la relación entre usuario y libro.
 */
@Getter
@Setter
public class UserBookDTO {

	/** Estado del libro para el usuario */
	private String status;

	/** Calificación del libro */
	private String rate;

	/** Comentario del usuario sobre el libro */
	private String comment;

}
