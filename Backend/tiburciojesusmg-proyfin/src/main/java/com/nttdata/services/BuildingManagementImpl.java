package com.nttdata.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.persistence.model.Book;
import com.nttdata.persistence.model.User;
import com.nttdata.persistence.repositories.BookRepositoryI;
import com.nttdata.persistence.repositories.UserRepositoryI;

@Service
public class BuildingManagementImpl implements BuildingManagementI {

	// Todas la validaciones van en la capa de servicio.

	@Autowired
	private BookRepositoryI bookRepo;

	@Autowired
	private UserRepositoryI userRepo;

	@Override
	public Book addBook(Book b) {

		return bookRepo.save(b);

	}

	@Override
	public void deleteBook(Long id) {

		bookRepo.deleteById(id);

	}

	@Override
	public List<Book> searchAllBooks() {

		return bookRepo.findAll();
	}

	@Override
	public List<Book> searchByTitle(String title) {

		return bookRepo.searchByTitle(title);
	}

	@Override
	public List<String> getAllBookTitles() {
		List<Book> books = bookRepo.findAll();
		return books.stream().map(Book::getTitle).toList();
	}

	@Override
	public List<Book> findBooksByUserId(Long userId) {

		return bookRepo.findBooksByUserId(userId);
	}

	@Override
	public void addBookToUser(Long userId, Book book) {
		Optional<User> userOptional = userRepo.findById(userId);

		if (userOptional.isPresent()) {
			User user = userOptional.get();
			Set<Book> books = user.getBooks();
			books.add(book);
			user.setBooks(books);
			userRepo.save(user);
		} else {
			// Manejar el caso en el que el usuario no existe
			// Puedes lanzar una excepción, devolver un código de error, etc.
		}
	}

}
