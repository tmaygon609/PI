package com.nttdata.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.persistence.model.Book;
import com.nttdata.persistence.model.User;
import com.nttdata.persistence.model.UserBook;
import com.nttdata.persistence.repositories.UserBookRepositoryI;

@Service
public class UserBookServiceIImpl implements UserBookServiceI {

	// Todas la validaciones van en la capa de servicio.

	@Autowired
	private UserBookRepositoryI userBookRepository;

	@Override
	public List<UserBook> getBooksByUser(Long userId) {

		return userBookRepository.findByUserId(userId);
	}

	public void registerBook(User user, Book book) {

		UserBook userBook = new UserBook();
		userBook.setUser(user);
		userBook.setBook(book);

		userBookRepository.save(userBook);
	}

}
