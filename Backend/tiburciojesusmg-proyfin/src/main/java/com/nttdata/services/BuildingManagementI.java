package com.nttdata.services;

import java.util.List;

import com.nttdata.persistence.model.Book;
import com.nttdata.persistence.model.User;

public interface BuildingManagementI {

	public void addBook(final Book b);

	public void deleteBook(final Long id);

	public List<Book> searchByTitle(String title);

	public List<Book> searchAllBooks();

	public List<String> getAllBookTitles();

	public void addBookForUser(final Book b, final User u, final Long rating);

}
