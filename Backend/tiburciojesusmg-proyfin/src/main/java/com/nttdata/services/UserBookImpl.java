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

}
