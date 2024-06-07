package com.nttdata.exceptions;

/**
 * Excepción lanzada cuando ocurre un error genérico.
 */
public class GeneralException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea una nueva instancia de GeneralException con el mensaje de error
	 * especificado.
	 *
	 * @param message el mensaje de error que describe la excepción
	 */
	public GeneralException(String message) {
		super(message);
	}
}
