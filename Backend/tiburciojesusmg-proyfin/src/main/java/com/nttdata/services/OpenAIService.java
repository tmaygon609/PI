package com.nttdata.services;

import java.util.ArrayList;
import java.util.List;

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

@Service
public class OpenAIService {

	private static final String OPENAI_URL = "https://api.openai.com/v1/chat/completions";
	private static final String AUTHORIZATION_HEADER = "Bearer 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";

	public OpenAIResponse getOpenAIResponse(OpenAIRequest request) {
		RestTemplate restTemplate = new RestTemplate();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.set("Authorization", AUTHORIZATION_HEADER);

		HttpEntity<OpenAIRequest> entity = new HttpEntity<>(request, headers);

		ResponseEntity<OpenAIResponse> response = restTemplate.exchange(OPENAI_URL, HttpMethod.POST, entity,
				OpenAIResponse.class);

		return response.getBody();
	}

	public OpenAIResponse getBookRecommendation() {
		OpenAIRequest request = new OpenAIRequest();
		request.setModel("gpt-3.5-turbo");
		request.setTemperature(0.7);

		List<Message> messages = new ArrayList<>();
		Message message = new Message();
		message.setRole("user");
		message.setContent(
				"Recomiéndame un solo libro similar a 'El señor de los anillos', 'Harry Potter' y 'Juego de tronos' en términos de temática y estilo literario. Por favor, proporciona una recomendación basada en estas obras, sin necesidad de explicaciones adicionales, formato: titulo, autor.");
		messages.add(message);

		request.setMessages(messages);

		return getOpenAIResponse(request);
	}

}
