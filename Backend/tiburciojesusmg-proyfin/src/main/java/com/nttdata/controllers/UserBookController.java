package com.nttdata.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.persistence.dto.UserBookDTO;
import com.nttdata.persistence.model.UserBook;
import com.nttdata.services.impl.UserBookImpl;

@RestController
@RequestMapping("/v1/usersBooks")
public class UserBookController {

	@Autowired
	private UserBookImpl userBookService;

	@GetMapping("/getUserDetails")
	public UserBook getUserBookDetails(@RequestParam Long userId, @RequestParam Long bookId) {
		return userBookService.getUserBookDetails(userId, bookId);
	}

	@PutMapping("/{id}")
	public UserBook updateUserBook(@PathVariable("id") Long id, @RequestBody UserBookDTO userBookDTO) {
		return userBookService.updateUserBook(id, userBookDTO.getStatus(), userBookDTO.getRate(),
				userBookDTO.getComment());
	}

	/**
	 * Buscar libros por título y ID de usuario.
	 *
	 * @param title  Título del libro a buscar.
	 * @param userId ID del usuario.
	 * @return ResponseEntity con la lista de libros encontrados.
	 */
	@GetMapping("/searchByBookIdAndUserId")
	public ResponseEntity<List<UserBook>> searchBooksByBookIdAndUserId(@RequestParam Long bookId,
			@RequestParam Long userId) {
		List<UserBook> userBooks = userBookService.searchBooksByBookIdAndUserId(bookId, userId);
		return ResponseEntity.ok().body(userBooks);
	}

}
