package com.nttdata.dao.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la solicitud de registro (sign-up).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequest {

	/**
	 * Nombre del usuario.
	 */
	private String name;

	/**
	 * Apellido del usuario.
	 */
	private String lastName;

	/**
	 * Nombre de usuario para el registro.
	 */
	private String user;

	/**
	 * Contraseña para el registro.
	 */
	private String password;

}
