package com.nttdata.dao.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la solicitud de inicio de sesión (signin).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequest {

	/**
	 * Nombre de usuario para el inicio de sesión.
	 */
	private String user;

	/**
	 * Contraseña para el inicio de sesión.
	 */
	private String password;

}
