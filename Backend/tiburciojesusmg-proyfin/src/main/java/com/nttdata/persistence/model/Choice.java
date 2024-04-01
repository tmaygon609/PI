package com.nttdata.persistence.model;

import com.nttdata.persistence.model.OpenAIRequest.Message;

public class Choice {

	/** Mensaje */
	private Message message;

	/**
	 * @return the message
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(Message message) {
		this.message = message;
	}

}
