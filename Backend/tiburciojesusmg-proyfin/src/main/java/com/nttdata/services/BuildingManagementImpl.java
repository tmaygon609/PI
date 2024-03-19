package com.nttdata.services;

import java.util.Collections;
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
	private UserRepositoryI userRepository;

	@Override
	public Book addBook(Book b) {

		return bookRepo.save(b);

	}

	@Override
	public void deleteBook(Long id) {

		Optional<Book> bookOptional = bookRepo.findById(id);

		if (bookOptional.isPresent()) {

			Book book = bookOptional.get();

			for (User user : book.getUsers()) {
				user.getBooks().remove(book);
				userRepository.save(user);
			}
			bookRepo.deleteById(id);
		} else {

			// Manejar el caso en el que el libro no existe
			// Puedes lanzar una excepción, devolver un código de error, etc.
		}

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
	public List<Book> searchByTitleAndUser(String title, Long userId) {

		return bookRepo.findByTitleAndUserId(title, userId);
	}

	@Override
	public List<String> getBookTitlesOfUser(Long userId) {
		List<Book> books = bookRepo.findBooksByUserId(userId);
		return books.stream().map(Book::getTitle).toList();
	}

	@Override
	public List<Book> findBooksByUserId(Long userId) {

		return bookRepo.findBooksByUserId(userId);
	}

	@Override
	public void addBookToUser(Long userId, Long bookId) {
		Optional<User> userOptional = userRepository.findById(userId);

		Optional<Book> bookOptional = bookRepo.findById(bookId);

		if (userOptional.isPresent() && bookOptional.isPresent()) {
			User user = userOptional.get();
			Book book = bookOptional.get();

			Set<Book> books = user.getBooks();
			books.add(book);
			user.setBooks(books);

			Set<User> users = book.getUsers();
			users.add(user);
			book.setUsers(users);

			userRepository.save(user);
			bookRepo.save(book);
		} else {
			// Manejar el caso en el que el usuario no existe
			// Puedes lanzar una excepción, devolver un código de error, etc.
		}

	}

	@Override
	public Set<Book> getBooksByUserId(Long userId) {
		Optional<User> userOptional = userRepository.findById(userId);

		if (userOptional.isPresent()) {
			return userOptional.get().getBooks();
		} else {
			return Collections.emptySet();
		}
	}

}
