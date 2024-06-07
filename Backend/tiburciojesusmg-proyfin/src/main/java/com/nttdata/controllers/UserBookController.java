package com.nttdata.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.nttdata.exceptions.UserBookNotFoundException;
import com.nttdata.persistence.dto.UserBookDTO;
import com.nttdata.persistence.model.UserBook;
import com.nttdata.services.impl.UserBookImpl;

/**
 * Controlador para manejar las solicitudes relacionadas con los libros de los
 * usuarios.
 */
@RestController
@RequestMapping("/v1/usersBooks")
public class UserBookController {

	@Autowired
	private UserBookImpl userBookService;

	/**
	 * Obtiene los detalles de un libro de usuario.
	 *
	 * @param userId ID del usuario.
	 * @param bookId ID del libro.
	 * @return ResponseEntity con los detalles del libro de usuario.
	 */
	@GetMapping("/getUserDetails")
	public ResponseEntity<UserBook> getUserBookDetails(@RequestParam Long userId, @RequestParam Long bookId) {

		UserBook userBook = userBookService.getUserBookDetails(userId, bookId);
		return ResponseEntity.ok().body(userBook);
	}

	/**
	 * Actualiza los detalles de un libro de usuario.
	 *
	 * @param id          ID del libro de usuario.
	 * @param userBookDTO Objeto UserBookDTO con los nuevos detalles del libro.
	 * @return ResponseEntity con el libro de usuario actualizado.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<UserBook> updateUserBook(@PathVariable("id") Long id, @RequestBody UserBookDTO userBookDTO) {
		UserBook updatedUserBook = userBookService.updateUserBook(id, userBookDTO.getStatus(), userBookDTO.getRate(),
				userBookDTO.getComment());
		return ResponseEntity.ok().body(updatedUserBook);
	}

	/**
	 * Busca libros por ID de libro y ID de usuario.
	 *
	 * @param bookId ID del libro.
	 * @param userId ID del usuario.
	 * @return ResponseEntity con la lista de libros encontrados.
	 */
	@GetMapping("/searchByBookIdAndUserId")
	public ResponseEntity<List<UserBook>> searchBooksByBookIdAndUserId(@RequestParam Long bookId,
			@RequestParam Long userId) {
		List<UserBook> userBooks = userBookService.searchBooksByBookIdAndUserId(bookId, userId);
		return ResponseEntity.ok().body(userBooks);
	}

	/**
	 * Elimina un libro de la relación UserBook por su ID.
	 *
	 * @param id ID del libro de usuario.
	 * @return ResponseEntity con un mensaje de éxito si se elimina correctamente.
	 */
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteBook(@PathVariable Long id) {

		userBookService.deleteBook(id);
		return ResponseEntity.ok().body("Libro borrado con éxito.");

	}

	/**
	 * Maneja la excepción cuando no se encuentra un libro de usuario.
	 *
	 * @param ex Excepción de libro de usuario no encontrado.
	 * @return ResponseEntity con un mensaje de error.
	 */
	@ExceptionHandler(UserBookNotFoundException.class)
	public ResponseEntity<String> handleUserBookNotFoundException(UserBookNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	/**
	 * Maneja excepciones generales.
	 *
	 * @param ex      Excepción general.
	 * @param request Objeto WebRequest.
	 * @return ResponseEntity con un mensaje de error interno.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error interno");
	}

}
