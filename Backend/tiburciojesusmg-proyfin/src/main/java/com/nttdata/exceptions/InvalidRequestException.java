package com.nttdata.exceptions;

/**
 * Excepción lanzada cuando se proporciona una solicitud inválida.
 */
public class InvalidRequestException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea una nueva instancia de InvalidRequestException con el mensaje de error
	 * especificado.
	 *
	 * @param message El mensaje de error que describe la excepción.
	 */
	public InvalidRequestException(String message) {
		super(message);
	}
}