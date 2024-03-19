package com.nttdata.controllers;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

	@GetMapping
	public List<Book> showBooks() {

		return buildingService.searchAllBooks();
	}

	@PostMapping(path = "/saveBook")
	public ResponseEntity<Book> saveBook(@RequestBody Book b) {

		Book savedBook = buildingService.addBook(b);

		return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
	}

	@PostMapping(path = "/addBookToUser")
	public ResponseEntity<String> addBookToUser(@RequestParam Long userId, @RequestParam Long bookId) {
		buildingService.addBookToUser(userId, bookId);
		return new ResponseEntity<>("Book added successfully", HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public void deleteBook(final @PathVariable Long id) {

		buildingService.deleteBook(id);
	}

	@GetMapping(path = "/searchByTitle")
	public List<Book> searchByTitle(@RequestParam String title) {
		return buildingService.searchByTitle(title);
	}

	@GetMapping(path = "/searchByTitleAndUser")
	public List<Book> searchByTitle(@RequestParam String title, @RequestParam Long userId) {
		return buildingService.searchByTitleAndUser(title, userId);
	}

	@GetMapping("/getBooksByUserId")
	public Set<Book> getBooksByUserId(@RequestParam Long userId) {
		return buildingService.getBooksByUserId(userId);
	}

	@PostMapping(path = "/getOpenAIResponse")
	public OpenAIResponse getOpenAIResponse(@RequestBody OpenAIRequest request) {
		return openAIService.getOpenAIResponse(request);
	}

	@PostMapping(path = "/getBookRecommendation")
	public ResponseEntity<OpenAIResponse> getBookRecommendation(@RequestParam Long userId) {
		try {
			OpenAIResponse response = openAIService.getBookRecommendation(userId);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
