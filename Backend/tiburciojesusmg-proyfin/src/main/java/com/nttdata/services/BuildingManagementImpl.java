package com.nttdata.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.persistence.model.Book;
import com.nttdata.persistence.model.User;
import com.nttdata.persistence.model.UserBook;
import com.nttdata.persistence.repositories.BookRepositoryI;
import com.nttdata.persistence.repositories.UserBookRepositoryI;

@Service
public class BuildingManagementImpl implements BuildingManagementI {

	// Todas la validaciones van en la capa de servicio.

	@Autowired
	private BookRepositoryI bookRepo;

	@Autowired
	private UserBookRepositoryI userBookRepo;

	@Override
	public void addBook(Book b) {

		bookRepo.save(b);

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
	public void addBookForUser(Book b, User u, Long rating) {
		UserBook userBook = new UserBook();
		userBook.setBook(b);
		userBook.setUser(u);
		userBook.setRating(rating);

		userBookRepo.save(userBook);

	}

}
