package com.nttdata.controllers;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.persistence.model.Book;
import com.nttdata.persistence.model.User;
import com.nttdata.persistence.repositories.BookRepositoryI;
import com.nttdata.persistence.repositories.UserRepositoryI;
import com.nttdata.services.BuildingManagementI;
import com.nttdata.services.UserBookMappingServiceI;

@RestController
@RequestMapping("/userBook")
public class UserBookMappingController {

	@Autowired
	private UserBookMappingServiceI userBookService;

	@Autowired
	private BuildingManagementI buildingService;

	@Autowired
	private UserRepositoryI userRepository;

	@Autowired
	private BookRepositoryI bookRepository;

	@GetMapping("/getBooksByUserId")
	public Set<Book> getBooksByUserId(@RequestParam Long userId) {
		return userBookService.getBooksByUserId(userId);
	}

	@PostMapping("/addMapping")
	public void addMapping(@RequestParam Long userId, @RequestParam Long bookId) {

		Optional<User> userOptional = userRepository.findById(userId);
		Optional<Book> bookOptional = bookRepository.findById(bookId);

		if (userOptional.isPresent() && bookOptional.isPresent()) {
			User user = userOptional.get();
			Book book = bookOptional.get();

			userBookService.addMapping(user, book);

			buildingService.addBookToUser(userId, book);

		} else {
			// Manejar el caso en el que el usuario o el libro no existen
			// Puedes lanzar una excepción, devolver un código de error, etc.
		}
	}

}
