package com.nttdata.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.exceptions.UserBookNotFoundException;
import com.nttdata.persistence.model.UserBook;
import com.nttdata.persistence.repositories.UserBookRepositoryI;
import com.nttdata.services.UserBookI;

/**
 * Implementación del servicio para la gestión de libros de usuarios.
 */
@Service
public class UserBookImpl implements UserBookI {

	@Autowired
	private UserBookRepositoryI userBookRepository;

	@Override
	public UserBook getUserBookDetails(Long userId, Long bookId) {

		UserBook userBook = userBookRepository.findByUserIdAndBookId(userId, bookId);

		if (userBook == null) {
			throw new UserBookNotFoundException(userId, bookId);
		}

		return userBook;
	}

	@Override
	public UserBook updateUserBook(Long id, String status, String rate, String comment) {
		Optional<UserBook> userBookOptional = userBookRepository.findById(id);

		if (userBookOptional.isPresent()) {
			UserBook userBook = userBookOptional.get();

			if (status != null) {
				userBook.setStatus(status);
			}
			if (rate != null) {
				userBook.setRate(rate);
			}
			if (comment != null) {
				userBook.setComment(comment);
			}

			return userBookRepository.save(userBook);
		} else {

			throw new UserBookNotFoundException(id);
		}
	}

	@Override
	public List<UserBook> searchBooksByBookIdAndUserId(Long bookId, Long userId) {
		List<UserBook> userBooks = userBookRepository.findByBookIdAndUserId(bookId, userId);

		return userBooks;
	}

	@Override
	public void deleteBook(Long id) {

		if (!userBookRepository.existsById(id)) {
			throw new UserBookNotFoundException(id);
		}
		userBookRepository.deleteById(id);

	}

}
