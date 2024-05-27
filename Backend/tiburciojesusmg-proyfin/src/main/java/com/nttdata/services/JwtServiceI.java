package com.nttdata.services;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * Servicio de JWT.
 */
public interface JwtServiceI {

	/**
	 * Extrae el nombre de usuario de un token JWT.
	 *
	 * @param token El token JWT.
	 * @return El nombre de usuario.
	 */
	String extractUserName(String token);

	/**
	 * Genera un token JWT para un usuario.
	 *
	 * @param userDetails Los detalles del usuario.
	 * @return El token JWT generado.
	 */
	String generateToken(UserDetails userDetails);

	/**
	 * Verifica si un token JWT es válido.
	 *
	 * @param token       El token JWT.
	 * @param userDetails Los detalles del usuario.
	 * @return true si el token es válido, false en caso contrario.
	 */
	boolean isTokenValid(String token, UserDetails userDetails);

}
