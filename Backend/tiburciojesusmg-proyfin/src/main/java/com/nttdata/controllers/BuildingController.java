package com.nttdata.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.persistence.model.Book;
import com.nttdata.persistence.model.OpenAIRequest;
import com.nttdata.persistence.model.OpenAIResponse;
import com.nttdata.services.BuildingManagementI;
import com.nttdata.services.OpenAIService;

@RestController
@RequestMapping("/building")
public class BuildingController {

	@Autowired
	private BuildingManagementI buildingService;

	@Autowired
	private OpenAIService openAIService;

//	@Autowired
//	private BuildingChatGPTService chatGPTService;

	@GetMapping
	public List<Book> showBooks() {

		return buildingService.searchAllBooks();
	}

	@PutMapping
	public void saveBook(final @RequestBody Book b) {

		buildingService.addBook(b);
	}

	@DeleteMapping
	public void deleteBook(final @RequestBody Book b) {

		buildingService.deleteBook(b);
	}

	@GetMapping(path = "/searchByTitle")
	public List<Book> searchByTitle(@RequestBody Book b) {
		return buildingService.searchByTitle(b.getTitle());
	}

	@PostMapping(path = "/getOpenAIResponse")
	public OpenAIResponse getOpenAIResponse(@RequestBody OpenAIRequest request) {
		return openAIService.getOpenAIResponse(request);
	}

//	@GetMapping(path = "/getResponseFromChatGPT")
//	public String getResponseFromChatGPT(@RequestParam String prompt){
//		return chatGPTService.getResponseFromChatGPT(prompt);
//	}
//	

}
