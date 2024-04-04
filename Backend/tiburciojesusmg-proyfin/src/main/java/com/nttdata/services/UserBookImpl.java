package com.nttdata.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.persistence.model.UserBook;
import com.nttdata.persistence.repositories.UserBookRepositoryI;

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

		UserBook userBook = userBookRepository.findByBookId(id);

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
	}
}
