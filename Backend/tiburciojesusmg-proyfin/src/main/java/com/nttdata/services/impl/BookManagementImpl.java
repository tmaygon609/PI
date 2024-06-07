package com.nttdata.services.impl;

import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nttdata.exceptions.BookNotFoundException;
import com.nttdata.exceptions.UserNotFoundException;
import com.nttdata.persistence.model.Book;
import com.nttdata.persistence.model.User;
import com.nttdata.persistence.model.UserBook;
import com.nttdata.persistence.repositories.BookRepositoryI;
import com.nttdata.persistence.repositories.UserBookRepositoryI;
import com.nttdata.persistence.repositories.UserRepositoryI;
import com.nttdata.services.BookManagementI;

@Service
@Transactional
public class BookManagementImpl implements BookManagementI {

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
	public Page<Book> searchAllBooks(Pageable pageable) {
		return bookRepository.findAll(pageable);
	}

	@Override
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Page<Book> searchByGenre(String genre, Pageable pageable) {
		return bookRepository.searchByGenre(genre, pageable);
	}

	@Override
	public List<Book> searchByTitle(String title) {

		return bookRepository.searchByTitleContaining(title);
	}

	@Override
	public List<String> getBookTitlesOfUser(Long userId) {
		List<Book> books = userBookRepository.findBooksByUserId(userId);
		return books.stream().map(Book::getTitle).toList();
	}

	@Override
	public void addBookToUser(UserBook userBook) {

		Optional<User> userOptional = userRepository.findById(userBook.getUser().getId());
		Optional<Book> bookOptional = bookRepository.findById(userBook.getBook().getId());

		if (userOptional.isPresent() && bookOptional.isPresent()) {

			User user = userOptional.get();
			Book book = bookOptional.get();

			// Crear una nueva entrada en la tabla de unión

			userBook.setUser(user);
			userBook.setBook(book);
			userBookRepository.save(userBook);

		} else {

			if (!userOptional.isPresent()) {
				throw new UserNotFoundException(userBook.getUser().getId());
			}
			if (!bookOptional.isPresent()) {
				throw new BookNotFoundException(userBook.getBook().getId());
			}
		}

	}

	@Override
	public Set<Book> getBooksByUserId(Long userId) {
		Optional<User> userOptional = userRepository.findById(userId);

		if (userOptional.isPresent()) {
			User user = userOptional.get();
			return user.getUserBooks().stream().map(UserBook::getBook).sorted(Comparator.comparing(Book::getTitle))
					.collect(Collectors.toCollection(LinkedHashSet::new)); // Utilizar LinkedHashSet para mantener el
																			// orden
		} else {
			throw new UserNotFoundException(userId);
		}
	}

	@Override
	public Book updateBook(Long id, String title, String author) {
		Optional<Book> bookOptional = bookRepository.findById(id);

		if (bookOptional.isPresent()) {
			Book book = bookOptional.get();

			if (title != null) {
				book.setTitle(title);
			}
			if (author != null) {
				book.setAuthor(author);
			}

			return bookRepository.save(book);
		} else {

			throw new BookNotFoundException(id);
		}
	}

	@Override
	public void removeBook(Long id) {

		Optional<Book> bookOptional = bookRepository.findById(id);

		if (bookOptional.isPresent()) {

			Book book = bookOptional.get();

			// Eliminar las relaciones UserBook primero
			Set<UserBook> userBooks = book.getUserBooks();
			for (UserBook userBook : userBooks) {
				userBookRepository.delete(userBook);
			}

			// Luego eliminar el libro
			bookRepository.deleteById(id);

		} else {

			throw new BookNotFoundException(id);
		}

	}

}
