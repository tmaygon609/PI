package com.nttdata.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object para la entidad Book.
 */
@Getter
@Setter
@AllArgsConstructor
public class BookDTO {

	/** ID del libro */
	private Long id;

	/** Título del libro */
	private String title;

	/** Autor del libro */
	private String author;

	/** Género del libro */
	private String genre;

	/** Imagen del libro en formato Base64 */
	private String base64Image;

}
