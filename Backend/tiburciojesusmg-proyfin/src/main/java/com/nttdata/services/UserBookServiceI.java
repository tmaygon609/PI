package com.nttdata.services;

import java.util.List;

import com.nttdata.persistence.model.Book;
import com.nttdata.persistence.model.User;
import com.nttdata.persistence.model.UserBook;

public interface UserBookServiceI {

	public List<UserBook> getBooksByUser(Long userId);

	public void registerBook(User user, Book book);

}
