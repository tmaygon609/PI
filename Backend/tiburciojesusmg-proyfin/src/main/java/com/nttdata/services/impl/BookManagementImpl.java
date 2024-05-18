package com.nttdata.services.impl;

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
import com.nttdata.services.BookManagementI;

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

//	@Override
//	public Book addBook(Book b) {
//		// Guarda el libro en la base de datos
//		Book savedBook = bookRepository.save(b);
//
//		// Guarda la imagen del libro en el servidor
//		if (savedBook.getImagenBytes() != null) {
//			try {
//
//				// Genera un nombre de archivo aleatorio usando un GUID
//				String fileName = UUID.randomUUID().toString() + ".jpg";
//
//				// Crea el directorio si no existe
//				FileUtils.forceMkdir(new File("src/main/resources/static/images"));
//
//				// Crea el archivo de imagen
//				File imageFile = new File("src/main/resources/static/images", fileName);
//
//				// Escribe los bytes de la imagen en el archivo
//				FileUtils.writeByteArrayToFile(imageFile, savedBook.getImagenBytes());
//			} catch (IOException e) {
//				// Maneja la excepción
//				e.printStackTrace();
//			}
//		}
//
//		return savedBook;
//	}

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

		return bookRepository.searchByTitleContaining(title);
	}

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
