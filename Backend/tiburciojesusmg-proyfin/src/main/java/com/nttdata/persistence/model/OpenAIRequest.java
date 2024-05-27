package com.nttdata.persistence.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa una solicitud a OpenAI.
 */
@Getter
@Setter
public class OpenAIRequest {

	/** Modelo de ChatGPT */
	private String model;

	/** Lista de mensajes */
	private List<Message> messages;

	/** Aleatoriedad de la respuesta */
	private double temperature;

	/**
	 * Clase interna que representa un mensaje en la solicitud.
	 */
	@Getter
	@Setter
	public static class Message {

		/** Rol del mensaje */
		private String role;

		/** Contenido del mensaje */
		private String content;

	}

}
