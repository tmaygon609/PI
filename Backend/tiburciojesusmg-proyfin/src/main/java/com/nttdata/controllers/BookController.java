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
import com.nttdata.persistence.model.UserBook;
import com.nttdata.services.BookManagementI;
import com.nttdata.services.OpenAIService;

@RestController
@RequestMapping("/v1/books")
public class BookController {

	@Autowired
	private BookManagementI bookService;

	@Autowired
	private OpenAIService openAIService;

	/**
	 * Obtener todos los libros.
	 * 
	 * @return ResponseEntity<List<Book>>
	 */
	@GetMapping
	public ResponseEntity<List<Book>> showBooks() {

		List<Book> books = bookService.searchAllBooks();

		return ResponseEntity.ok().body(books);
	}

	/**
	 * Buscar libros por género.
	 * 
	 * @param genre
	 * @return ResponseEntity<List<Book>>
	 */
	@GetMapping("/searchByGenre")
	public ResponseEntity<List<Book>> searchBooksByGenre(@RequestParam String genre) {
		List<Book> books = bookService.searchByGenre(genre);
		return ResponseEntity.ok().body(books);
	}

	/**
	 * Buscar libros por título.
	 * 
	 * @param title
	 * @return ResponseEntity<List<Book>>
	 */
	@GetMapping(path = "/searchByTitle")
	public ResponseEntity<List<Book>> searchBooksByTitle(@RequestParam String title) {
		List<Book> books = bookService.searchByTitle(title);
		return ResponseEntity.ok().body(books);
	}

	/**
	 * Obtiene los libros de un usuario específico.
	 * 
	 * @param userId
	 * @return ResponseEntity<Set<Book>>
	 */
	@GetMapping("/users/{userId}")
	public ResponseEntity<Set<Book>> getBooksByUserId(@PathVariable Long userId) {
		Set<Book> userBooks = bookService.getBooksByUserId(userId);
		return ResponseEntity.ok().body(userBooks);
	}

//	@GetMapping
//	public Page<Book> showBooks(int page, int size, String sortBy) {
//
//		PageRequest pageRequest = PageRequest.of(page, size, Sort.by(sortBy));
//		return bookService.searchAllBooks(pageRequest);
//	}

	/**
	 * Registra/crea un libro.
	 * 
	 * @param book
	 * @return ResponseEntity<Book>
	 */
	@PostMapping
	public ResponseEntity<Book> saveBook(@RequestBody Book book) {

		Book savedBook = bookService.addBook(book);

		return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
	}

	/**
	 * Crea un UserBook, relación entre un libro y un usuario.
	 * 
	 * @param userBook
	 * @return ResponseEntity<String>
	 */
	@PostMapping(path = "/addBookToUser")
	public ResponseEntity<String> addBookToUser(@RequestBody UserBook userBook) {
		bookService.addBookToUser(userBook);
		return ResponseEntity.status(HttpStatus.CREATED).body("Creada relación de usuario y libro.");
	}

	/**
	 * Eliminar un libro por id.
	 * 
	 * @param id
	 * @return ResponseEntity<String>
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteBook(@PathVariable Long id) {
		try {
			bookService.deleteBook(id);
			return ResponseEntity.ok().body("Libro borrado con éxito.");
		} catch (Exception e) {
			// Manejar cualquier excepción que pueda ocurrir durante la eliminación del
			// libro
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al borrar el libro.");
		}
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
