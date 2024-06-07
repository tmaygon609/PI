package com.nttdata.exceptions;

/**
 * Excepción lanzada cuando no se encuentra un usuario.
 */
public class UserNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Crea una nueva instancia de UserNotFoundException cuando no se encuentra un
	 * usuario por su ID.
	 *
	 * @param id El ID del usuario.
	 */
	public UserNotFoundException(Long id) {
		super("No se encontró el usuario con ID: " + id);
	}

	/**
	 * Crea una nueva instancia de UserNotFoundException cuando no se encuentra un
	 * usuario por su nombre de usuario.
	 *
	 * @param username El nombre de usuario.
	 */
	public UserNotFoundException(String username) {
		super("No se encontró el usuario con nombre de usuario: " + username);
	}
}
