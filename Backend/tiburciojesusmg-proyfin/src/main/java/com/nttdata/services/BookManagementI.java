package com.nttdata.services;

import java.util.List;
import java.util.Set;

import com.nttdata.persistence.model.Book;

public interface BookManagementI {

	public Book addBook(final Book b);

	public void deleteBook(final Long id);

	public List<Book> searchByTitle(String title);

	public List<Book> searchByTitleAndUser(String title, Long userId);

	public List<Book> searchAllBooks();

	public List<String> getBookTitlesOfUser(Long userId);

	public List<Book> findBooksByUserId(final Long userId);

	void addBookToUser(Long userId, Long bookId, String status, String rate, String comment);

	Set<Book> getBooksByUserId(Long userId);

}
