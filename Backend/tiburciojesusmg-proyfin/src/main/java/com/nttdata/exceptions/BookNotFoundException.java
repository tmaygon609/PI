package com.nttdata.exceptions;

/**
 * Excepción lanzada cuando no se encuentra un libro.
 */
public class BookNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea una nueva instancia de BookNotFoundException con el ID del libro no
	 * encontrado.
	 *
	 * @param id el ID del libro que no se encontró
	 */
	public BookNotFoundException(Long id) {
		super("No se encontró el libro con ID: " + id);
	}
}
