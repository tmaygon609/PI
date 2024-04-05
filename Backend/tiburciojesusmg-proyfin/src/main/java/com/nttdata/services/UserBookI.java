package com.nttdata.services;

import com.nttdata.persistence.model.UserBook;

public interface UserBookI {

	public UserBook getUserBookDetails(Long userId, Long bookId);

	public UserBook updateUserBook(Long id, String status, String rate, String comment);

}
