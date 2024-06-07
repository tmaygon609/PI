package com.nttdata.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Configuración para la codificación de contraseñas.
 */
@Configuration
public class PasswordConfig {

	/**
	 * Método para proporcionar un bean de codificador de contraseñas.
	 *
	 * @return BCryptPasswordEncoder Instancia de BCryptPasswordEncoder para
	 *         codificar contraseñas.
	 */
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
