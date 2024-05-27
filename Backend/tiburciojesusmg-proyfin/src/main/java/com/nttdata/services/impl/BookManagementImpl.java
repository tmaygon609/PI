package com.nttdata.services.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
			// Manejar el caso en el que el usuario no existe
			// Puedes lanzar una excepción, devolver un código de error, etc.
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
			return Collections.emptySet();
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
			// Si no se encuentra el libro con el ID proporcionado, puedes manejarlo de
			// acuerdo a tu lógica de negocio.
			throw new NoSuchElementException("No se encontró el libro con ID: " + id);
		}
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

			// Manejar el caso en el que el libro no existe
			// Puedes lanzar una excepción, devolver un código de error, etc.
		}

	}

//	@Override
//	public List<Book> findBooksByUserId(Long userId) {
//
//		return userBookRepository.findBooksByUserId(userId);
//	}

//	@Override
//	public List<Book> searchAllBooks() {
//
//		Sort sort = Sort.by(Sort.Direction.ASC, "title");
//
//		return bookRepository.findAll(sort);
//	}

//	@Override
//	public List<Book> searchByGenre(String genre) {
//
//		return bookRepository.searchByGenre(genre);
//	}
//	

//	@Override
//	public List<Book> searchByTitleOrAuthorOrGenre(String title, String author, String genre) {
//		if (title != null && !title.trim().isEmpty()) {
//		    // Buscar por título
//		    return bookRepository.searchByTitleContaining(title);
//		} else if (author != null && !author.trim().isEmpty()) {
//		    // Buscar por autor
//		    return bookRepository.searchByAuthorContaining(author);
//		} else if (genre != null && !genre.trim().isEmpty()) {
//		    // Buscar por género
//		    return bookRepository.searchByGenre(genre);
//		} else {
//		    // Si no se proporciona ningún parámetro, retornar una lista vacía
//		    return Collections.emptyList();
//		}
//	}

//	@Override
//	public List<Book> searchByTitleAndUser(String title, Long userId) {
//
//		return userBookRepository.findByBookTitleAndUserId(title, userId);
//	}

}
