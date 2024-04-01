package com.nttdata.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nttdata.persistence.model.User;
import com.nttdata.persistence.repositories.UserRepositoryI;

@Service
public class UserManagementImpl implements UserManagementI {

	// Todas la validaciones van en la capa de servicio.

	@Autowired
	private UserRepositoryI userRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User login(String user, String password) {

		User userEntity = userRepo.findByUser(user);

		if (userEntity != null && passwordEncoder.matches(password, userEntity.getPassword())) {

			return userEntity;
		}

		return null;
	}

	@Override
	public void addUser(User u) {

		u.setPassword(passwordEncoder.encode(u.getPassword()));

		userRepo.save(u);

	}

}
