package com.nttdata.services;

import java.util.List;
import java.util.Set;

import com.nttdata.persistence.model.Book;
import com.nttdata.persistence.model.UserBook;

public interface BookManagementI {

	public Book addBook(final Book b);

	public void deleteBook(final Long id);

	public List<Book> searchByTitle(String title);

//	public List<Book> searchByTitleAndUser(String title, Long userId);

	public List<Book> searchAllBooks();

//	public Page<Book> searchAllBooks(Pageable pageable);

	public List<Book> searchByGenre(String genre);

	public List<String> getBookTitlesOfUser(Long userId);

	public List<Book> findBooksByUserId(final Long userId);

	void addBookToUser(UserBook userBook);

	Set<Book> getBooksByUserId(Long userId);

//	public Book addBookWithImage(Book book, MultipartFile image) throws IOException;

}
