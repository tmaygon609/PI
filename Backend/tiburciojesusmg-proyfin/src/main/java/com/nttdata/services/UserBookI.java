package com.nttdata.services;

import com.nttdata.persistence.model.UserBook;

public interface UserBookI {

	public UserBook getUserBookDetails(Long userId, Long bookId);

}
