package com.nttdata.services.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.persistence.model.UserBook;
import com.nttdata.persistence.repositories.UserBookRepositoryI;
import com.nttdata.services.UserBookI;

@Service
public class UserBookImpl implements UserBookI {

	@Autowired
	private UserBookRepositoryI userBookRepository;

	@Override
	public UserBook getUserBookDetails(Long userId, Long bookId) {

		UserBook userBook = userBookRepository.findByUserIdAndBookId(userId, bookId);

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
			// Si no se encuentra el UserBook con el ID proporcionado, puedes manejarlo de
			// acuerdo a tu lógica de negocio.
			throw new NoSuchElementException("No se encontró el UserBook con ID: " + id);
		}
	}

	public List<UserBook> searchBooksByBookIdAndUserId(Long bookId, Long userId) {
		return userBookRepository.findByBookIdAndUserId(bookId, userId);
	}

}
