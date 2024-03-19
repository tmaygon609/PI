package com.nttdata.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.persistence.model.Book;
import com.nttdata.persistence.model.OpenAIRequest;
import com.nttdata.persistence.model.OpenAIResponse;
import com.nttdata.persistence.model.User;
import com.nttdata.services.BuildingManagementI;
import com.nttdata.services.OpenAIService;
import com.nttdata.services.UserManagementI;

@RestController
@RequestMapping("/building")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class BuildingController {

	@Autowired
	private BuildingManagementI buildingService;

	@Autowired
	private OpenAIService openAIService;

	@Autowired
	private UserManagementI userService;

	@GetMapping
	public List<Book> showBooks() {

		return buildingService.searchAllBooks();
	}

	@PutMapping
	public void saveBook(final @RequestBody Book b) {

		buildingService.addBook(b);
	}

	@DeleteMapping("/{id}")
	public void deleteBook(final @PathVariable Long id) {

		buildingService.deleteBook(id);
	}

	@GetMapping(path = "/searchByTitle")
	public List<Book> searchByTitle(@RequestParam String title) {
		return buildingService.searchByTitle(title);
	}

	@PostMapping(path = "/getOpenAIResponse")
	public OpenAIResponse getOpenAIResponse(@RequestBody OpenAIRequest request) {
		return openAIService.getOpenAIResponse(request);
	}

	@PostMapping(path = "/getBookRecommendation")
	public ResponseEntity<OpenAIResponse> getBookRecommendation() {
		try {
			OpenAIResponse response = openAIService.getBookRecommendation();
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			// Manejar excepciones según tus necesidades
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/registerBookForUser")
	public ResponseEntity<String> registerBookForUser(@RequestBody Book book, @RequestParam Long userId,
			@RequestParam Long rating) {

		try {
			User user = userService.findUserById(userId);
			buildingService.addBookForUser(book, user, rating);
			return new ResponseEntity<>("Libro registrado con éxito para el usuario.", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error al registrar el libro para el usuario.",
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
