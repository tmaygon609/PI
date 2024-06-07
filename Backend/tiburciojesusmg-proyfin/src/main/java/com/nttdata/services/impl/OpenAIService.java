package com.nttdata.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.nttdata.persistence.model.OpenAIRequest;
import com.nttdata.persistence.model.OpenAIRequest.Message;
import com.nttdata.persistence.model.OpenAIResponse;
import com.nttdata.services.BookManagementI;

/**
 * Servicio para interactuar con la API de OpenAI y obtener respuestas generadas
 * por IA.
 */
@Service
public class OpenAIService {

	@Autowired
	private BookManagementI buildingManagement;

	/**
	 * Clave de la API de OpenAI utilizada para la autenticación en las solicitudes.
	 */
	@Value("${openai.api.key}")
	private String apiKey;

	/**
	 * URL base para enviar solicitudes de completado de chat a la API de OpenAI.
	 */
	private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";

	/**
	 * Obtiene una respuesta de OpenAI utilizando la solicitud proporcionada.
	 *
	 * @param request La solicitud de OpenAI.
	 * @return La respuesta generada por OpenAI.
	 */
	public OpenAIResponse getOpenAIResponse(OpenAIRequest request) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", "Bearer " + apiKey);

		System.out.println("Api key: " + apiKey);

		HttpEntity<OpenAIRequest> entity = new HttpEntity<>(request, headers);

		ResponseEntity<OpenAIResponse> response = restTemplate.exchange(OPENAI_URL, HttpMethod.POST, entity,
				OpenAIResponse.class);

		return response.getBody();
	}

	/**
	 * Obtiene una recomendación de libro de OpenAI para un usuario específico.
	 *
	 * @param userId El ID del usuario para el que se solicita la recomendación.
	 * @return La respuesta generada por OpenAI que contiene la recomendación de
	 *         libro.
	 */
	public OpenAIResponse getBookRecommendation(Long userId) {
		OpenAIRequest request = new OpenAIRequest();
		request.setModel("gpt-3.5-turbo");
		request.setTemperature(0.8);

		List<Message> messages = new ArrayList<>();
		Message message = new Message();
		message.setRole("user");

		List<String> bookTitles = buildingManagement.getBookTitlesOfUser(userId);
		System.out.println("Book Titles: " + bookTitles);

		String content = "Recomiéndame solo un libro similar a '" + String.join("', '", bookTitles)
				+ "' en términos de temática y estilo literario. Por favor, proporciona una recomendación basada en estas obras, sin necesidad de explicaciones adicionales, el formato de la respuesta quiero que siempre sea: 'titulo', autor.";
		message.setContent(content);
		messages.add(message);

		request.setMessages(messages);

		return getOpenAIResponse(request);
	}

}
