package com.nttdata.services;

import com.nttdata.dao.request.SignUpRequest;
import com.nttdata.dao.request.SigninRequest;
import com.nttdata.dao.response.JwtAuthenticationResponse;

/**
 * Servicio de autenticación.
 */
public interface AuthenticationServiceI {

	/**
	 * Registra un nuevo usuario.
	 *
	 * @param request Datos de registro.
	 * @return Respuesta con el JWT.
	 */
	JwtAuthenticationResponse signup(SignUpRequest request);

	/**
	 * Autentica un usuario.
	 *
	 * @param request Datos de inicio de sesión.
	 * @return Respuesta con el JWT.
	 */
	JwtAuthenticationResponse signin(SigninRequest request);

}
