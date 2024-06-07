package com.nttdata.exceptions;

/**
 * Excepción lanzada cuando no se encuentra una relación UserBook.
 */
public class UserBookNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea una nueva instancia de UserBookNotFoundException cuando no se encuentra
	 * una relación UserBook para el usuario y libro especificados.
	 *
	 * @param userId El ID del usuario.
	 * @param bookId El ID del libro.
	 */
	public UserBookNotFoundException(Long userId, Long bookId) {
		super("No se encontró UserBook para el usuario con ID: " + userId + " y libro con ID: " + bookId);
	}

	/**
	 * Crea una nueva instancia de UserBookNotFoundException cuando no se encuentra
	 * una relación UserBook con el ID especificado.
	 *
	 * @param userBookId El ID de la relación UserBook.
	 */
	public UserBookNotFoundException(Long userBookId) {
		super("No se encontró UserBook con ID: " + userBookId);
	}
}
