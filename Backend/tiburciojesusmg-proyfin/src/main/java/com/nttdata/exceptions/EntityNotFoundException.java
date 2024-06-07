package com.nttdata.exceptions;

/**
 * Excepción lanzada cuando no se puede encontrar una entidad específica.
 */
public class EntityNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea una nueva instancia de EntityNotFoundException con el mensaje de error
	 * especificado.
	 *
	 * @param message el mensaje de error que describe la excepción
	 */
	public EntityNotFoundException(String message) {
		super(message);
	}
}
