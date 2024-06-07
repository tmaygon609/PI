package com.nttdata.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.exceptions.BookNotFoundException;
import com.nttdata.exceptions.UserNotFoundException;
import com.nttdata.persistence.dto.BookDTO;
import com.nttdata.persistence.model.Book;
import com.nttdata.persistence.model.OpenAIRequest;
import com.nttdata.persistence.model.OpenAIResponse;
import com.nttdata.persistence.model.UserBook;
import com.nttdata.services.BookManagementI;
import com.nttdata.services.impl.OpenAIService;

/**
 * Controlador para manejar las solicitudes de libros.
 */
@RestController
@RequestMapping("/v1/books")
public class BookController {

	@Autowired
	private BookManagementI bookService;

	@Autowired
	private OpenAIService openAIService;

	/**
	 * Obtiene una página de libros.
	 *
	 * @param page el número de página
	 * @param size el tamaño de la página
	 * @return ResponseEntity con la página de libros
	 */
	@GetMapping
	public ResponseEntity<Page<Book>> showBooks(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		Page<Book> books = bookService.searchAllBooks(PageRequest.of(page, size));
		return ResponseEntity.ok().body(books);
	}

	/**
	 * Obtiene todos los libros.
	 *
	 * @return ResponseEntity con la lista de libros
	 */
	@GetMapping("/all")
	public ResponseEntity<List<Book>> getAllBooks() {
		List<Book> books = bookService.getAllBooks();
		if (!books.isEmpty()) {
			return new ResponseEntity<>(books, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Busca libros por género.
	 *
	 * @param genre el género a buscar
	 * @param page  el número de página
	 * @param size  el tamaño de la página
	 * @return ResponseEntity con la página de libros encontrados
	 */
	@GetMapping("/searchByGenre")
	public ResponseEntity<Page<Book>> searchBooksByGenre(@RequestParam String genre,
			@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
		Page<Book> booksPage = bookService.searchByGenre(genre, PageRequest.of(page, size));
		return ResponseEntity.ok().body(booksPage);
	}

	/**
	 * Busca libros por título.
	 *
	 * @param title el título a buscar
	 * @return ResponseEntity con la lista de libros encontrados
	 */
	@GetMapping(path = "/searchByTitle")
	public ResponseEntity<List<Book>> searchBooksByTitle(@RequestParam String title) {
		List<Book> books = bookService.searchByTitle(title);
		return ResponseEntity.ok().body(books);
	}

	/**
	 * Obtiene los libros de un usuario específico.
	 *
	 * @param userId el ID del usuario
	 * @return ResponseEntity con el conjunto de libros del usuario
	 */
	@GetMapping("/users/{userId}")
	public ResponseEntity<Set<Book>> getBooksByUserId(@PathVariable Long userId) {
		Set<Book> userBooks = bookService.getBooksByUserId(userId);
		return ResponseEntity.ok().body(userBooks);
	}

	/**
	 * Registra/crea un libro.
	 *
	 * @param book el libro a registrar
	 * @return ResponseEntity con el libro registrado
	 * @throws IOException si hay un error de E/S
	 */
	@PostMapping
	public ResponseEntity<Book> uploadLibro(@RequestBody Book book) {

		Book savedBook = bookService.addBook(book);

		// Ejemplo de respuesta exitosa
		return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);

	}

	/**
	 * Crea un UserBook, relación entre un libro y un usuario.
	 *
	 * @param userBook la relación entre el libro y el usuario
	 * @return ResponseEntity con un mensaje de éxito
	 */
	@PostMapping(path = "/addBookToUser")
	public ResponseEntity<String> addBookToUser(@RequestBody UserBook userBook) {
		bookService.addBookToUser(userBook);
		return ResponseEntity.status(HttpStatus.CREATED).body("Creada relación de usuario y libro.");
	}

	/**
	 * Elimina un libro por ID.
	 *
	 * @param id el ID del libro a eliminar
	 * @return ResponseEntity con un mensaje de éxito
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<String> removeBook(@PathVariable Long id) {

		bookService.removeBook(id);
		return ResponseEntity.ok().body("Libro borrado con éxito.");

	}

	/**
	 * Actualiza un libro por ID.
	 *
	 * @param id      el ID del libro a actualizar
	 * @param bookDTO los datos actualizados del libro
	 * @return ResponseEntity con el libro actualizado
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Book> updateBook(@PathVariable("id") Long id, @RequestBody BookDTO bookDTO) {
		Book updatedBook = bookService.updateBook(id, bookDTO.getTitle(), bookDTO.getAuthor());
		return ResponseEntity.status(HttpStatus.OK).body(updatedBook);
	}

	/**
	 * Obtiene la respuesta de OpenAI para una solicitud dada.
	 *
	 * @param request la solicitud a enviar a OpenAI
	 * @return la respuesta de OpenAI
	 */
	@PostMapping(path = "/getOpenAIResponse")
	public OpenAIResponse getOpenAIResponse(@RequestBody OpenAIRequest request) {
		return openAIService.getOpenAIResponse(request);
	}

	/**
	 * Obtiene una recomendación de libro para un usuario dado.
	 *
	 * @param userId el ID del usuario para el que se desea obtener la recomendación
	 *               de libro
	 * @return ResponseEntity con la recomendación de libro
	 */
	@PostMapping(path = "/getBookRecommendation")
	public ResponseEntity<OpenAIResponse> getBookRecommendation(@RequestParam Long userId) {
		try {
			OpenAIResponse response = openAIService.getBookRecommendation(userId);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {

			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Maneja la excepción cuando no se encuentra un libro.
	 *
	 * @param ex la excepción de libro no encontrado
	 * @return ResponseEntity con un mensaje de error
	 */
	@ExceptionHandler(BookNotFoundException.class)
	public ResponseEntity<String> handleBookNotFoundException(BookNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	/**
	 * Maneja la excepción cuando no se encuentra un usuario.
	 *
	 * @param ex la excepción de usuario no encontrado
	 * @return ResponseEntity con un mensaje de error
	 */
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	/**
	 * Maneja excepciones generales.
	 *
	 * @param ex la excepción general
	 * @return ResponseEntity con un mensaje de error interno
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error interno");
	}

}
