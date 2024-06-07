package com.nttdata.exceptions;

/**
 * Excepción lanzada cuando ocurre un error relacionado con la base de datos.
 */
public class DatabaseException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea una nueva instancia de DatabaseException con el mensaje de error
	 * especificado.
	 *
	 * @param message el mensaje de error que describe la excepción
	 */
	public DatabaseException(String message) {
		super(message);
	}
}
