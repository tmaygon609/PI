package com.nttdata.services;

import java.util.List;

import com.nttdata.persistence.model.Book;

public interface BuildingManagementI {

	public Book addBook(final Book b);

	public void deleteBook(final Long id);

	public List<Book> searchByTitle(String title);

	public List<Book> searchAllBooks();

	public List<String> getAllBookTitles();

	public List<Book> findBooksByUserId(final Long userId);

	void addBookToUser(Long userId, Book book);

}
