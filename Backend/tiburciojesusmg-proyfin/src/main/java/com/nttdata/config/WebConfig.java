package com.nttdata.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Clase de configuración para la gestión de CORS en la aplicación.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

	/**
	 * Método para configurar el mapeo de CORS.
	 *
	 * @param registry CorsRegistry objeto de registro de CORS.
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("https://localhost").allowedMethods("GET", "POST", "PUT", "DELETE")
				.allowedHeaders("*").exposedHeaders("Authorization").allowCredentials(true);
	}
}
