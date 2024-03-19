package com.nttdata.services;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.persistence.model.Book;
import com.nttdata.persistence.model.User;
import com.nttdata.persistence.model.UserBookMapping;
import com.nttdata.persistence.repositories.UserBookMappingRepositoryI;
import com.nttdata.persistence.repositories.UserRepositoryI;

@Service
public class UserBookMappingServiceImpl implements UserBookMappingServiceI {

	@Autowired
	private UserBookMappingRepositoryI userBookMappingRepository;

	@Autowired
	private UserRepositoryI userRepository;

	@Override
	public void addMapping(User user, Book book) {

		UserBookMapping userBookMapping = new UserBookMapping();

		userBookMapping.setUser(user);
		userBookMapping.setBook(book);

		userBookMappingRepository.save(userBookMapping);
	}

	@Override
	public void removeMapping(User user, Book book) {
		// Lógica para eliminar el mapeo entre el usuario y el libro
	}

	@Override
	public Set<Book> getBooksByUserId(Long userId) {
		Optional<User> userOptional = userRepository.findById(userId);

		if (userOptional.isPresent()) {
			return userOptional.get().getBooks();
		} else {
			return Collections.emptySet();
		}
	}
}
