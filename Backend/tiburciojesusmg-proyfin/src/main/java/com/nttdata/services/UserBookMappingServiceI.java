package com.nttdata.services;

import java.util.Set;

import com.nttdata.persistence.model.Book;
import com.nttdata.persistence.model.User;

public interface UserBookMappingServiceI {

	void addMapping(User user, Book book);

	void removeMapping(User user, Book book);

	Set<Book> getBooksByUserId(Long userId);

}
