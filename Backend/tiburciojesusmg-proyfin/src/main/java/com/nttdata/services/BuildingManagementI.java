package com.nttdata.services;

import java.util.List;

import com.nttdata.persistence.model.Book;

public interface BuildingManagementI {
	
	public void addBook(final Book b);
	
	public void deleteBook(final Book b);
	
	public List<Book> searchByTitle(String title);
	
	public List<Book> searchAllBooks();	

}
