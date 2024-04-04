package com.nttdata.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.nttdata.persistence.model.Book;
import com.nttdata.persistence.model.User;
import com.nttdata.persistence.model.UserBook;
import com.nttdata.persistence.repositories.BookRepositoryI;
import com.nttdata.persistence.repositories.UserBookRepositoryI;
import com.nttdata.persistence.repositories.UserRepositoryI;

@Service
public class BookManagementImpl implements BookManagementI {

	// Todas la validaciones van en la capa de servicio.

	@Autowired
	private BookRepositoryI bookRepository;

	@Autowired
	private UserRepositoryI userRepository;

	@Autowired
	private UserBookRepositoryI userBookRepository;

	@Override
	public Book addBook(Book b) {

		return bookRepository.save(b);

	}

	@Override
	public void deleteBook(Long id) {

		Optional<Book> bookOptional = bookRepository.findById(id);

		if (bookOptional.isPresent()) {

			Book book = bookOptional.get();

			for (UserBook userBook : book.getUserBooks()) {
				User user = userBook.getUser();

				userBookRepository.findByUserAndBook(user, book);

				userBookRepository.delete(userBook);
			}
			bookRepository.deleteById(id);
		} else {

			// Manejar el caso en el que el libro no existe
			// Puedes lanzar una excepción, devolver un código de error, etc.
		}

	}

	@Override
	public List<Book> searchAllBooks() {

		Sort sort = Sort.by(Sort.Direction.ASC, "title");

		return bookRepository.findAll(sort);
	}

//	@Override
//	public Page<Book> searchAllBooks(Pageable pageable) {
//		return bookRepository.findAll(pageable);
//	}

	@Override
	public List<Book> searchByGenre(String genre) {

		return bookRepository.searchByGenre(genre);
	}

	@Override
	public List<Book> searchByTitle(String title) {

		return bookRepository.searchByTitle(title);
	}

	@Override
	public List<Book> searchByTitleAndUser(String title, Long userId) {

		return userBookRepository.findByTitleAndUserId(title, userId);
	}

	@Override
	public List<String> getBookTitlesOfUser(Long userId) {
		List<Book> books = userBookRepository.findBooksByUserId(userId);
		return books.stream().map(Book::getTitle).toList();
	}

	@Override
	public List<Book> findBooksByUserId(Long userId) {

		return userBookRepository.findBooksByUserId(userId);
	}

	@Override
	public void addBookToUser(Long userId, Long bookId, String status, String rate, String comment) {

		Optional<User> userOptional = userRepository.findById(userId);
		Optional<Book> bookOptional = bookRepository.findById(bookId);

		if (userOptional.isPresent() && bookOptional.isPresent()) {

			User user = userOptional.get();
			Book book = bookOptional.get();

			// Crear una nueva entrada en la tabla de unión
			UserBook userBook = new UserBook();
			userBook.setUser(user);
			userBook.setBook(book);
			userBook.setStatus(status);
			userBook.setRate(rate);
			userBook.setComment(comment);

			userBookRepository.save(userBook);

//			User user = userOptional.get();
//			Book book = bookOptional.get();
//
//			Set<Book> books = user.getBooks();
//			books.add(book);
//			user.setBooks(books);
//
//			Set<User> users = book.getUsers();
//			users.add(user);
//			book.setUsers(users);
//
//			userRepository.save(user);
//			bookRepo.save(book);
		} else {
			// Manejar el caso en el que el usuario no existe
			// Puedes lanzar una excepción, devolver un código de error, etc.
		}

	}

	@Override
	public Set<Book> getBooksByUserId(Long userId) {
		Optional<User> userOptional = userRepository.findById(userId);

		if (userOptional.isPresent()) {
			User user = userOptional.get();
			return user.getUserBooks().stream().map(UserBook::getBook).collect(Collectors.toSet());
		} else {
			return Collections.emptySet();
		}
	}

}
