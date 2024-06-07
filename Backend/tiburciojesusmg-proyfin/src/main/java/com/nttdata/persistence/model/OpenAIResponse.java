package com.nttdata.persistence.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa una respuesta de OpenAI.
 */
@Getter
@Setter
public class OpenAIResponse {

	/** Lista de opciones de respuesta */
	private List<Choice> choices;

}
