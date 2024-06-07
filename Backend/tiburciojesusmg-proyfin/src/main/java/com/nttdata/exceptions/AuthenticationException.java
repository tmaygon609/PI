package com.nttdata.exceptions;

/**
 * Excepción lanzada cuando ocurre un error durante el proceso de autenticación.
 */
public class AuthenticationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea una nueva instancia de AuthenticationException con el mensaje
	 * especificado.
	 *
	 * @param message el mensaje que describe la excepción
	 */
	public AuthenticationException(String message) {
		super(message);
	}
}
