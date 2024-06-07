package com.nttdata.exceptions;

/**
 * Excepción lanzada cuando un usuario ya existe en el sistema.
 */
public class UserAlreadyExistsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea una nueva instancia de UserAlreadyExistsException con el mensaje de
	 * error especificado.
	 *
	 * @param message El mensaje de error que describe la excepción.
	 */
	public UserAlreadyExistsException(String message) {
		super(message);
	}
}
