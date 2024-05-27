package com.nttdata.persistence.model;

import com.nttdata.persistence.model.OpenAIRequest.Message;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase que representa una elección.
 */
@Getter
@Setter
public class Choice {

	/** Mensaje asociado */
	private Message message;

}
