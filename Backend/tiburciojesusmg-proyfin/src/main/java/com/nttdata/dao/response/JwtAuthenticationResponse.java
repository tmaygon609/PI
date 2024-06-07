package com.nttdata.dao.response;

import com.nttdata.persistence.model.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Clase que representa la respuesta de autenticación JWT.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {

	/**
	 * Token de acceso JWT.
	 */
	private String accessToken;

	/**
	 * Usuario asociado a la respuesta de autenticación.
	 */
	private User user;
}
