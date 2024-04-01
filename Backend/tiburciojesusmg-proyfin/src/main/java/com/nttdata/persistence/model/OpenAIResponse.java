package com.nttdata.persistence.model;

import java.util.List;

public class OpenAIResponse {

	/** Lista de opciones */
	private List<Choice> choices;

	/**
	 * @return the choices
	 */
	public List<Choice> getChoices() {
		return choices;
	}

	/**
	 * @param choices the choices to set
	 */
	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

}
