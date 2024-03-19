package com.nttdata.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.persistence.model.User;
import com.nttdata.persistence.repositories.UserRepositoryI;

@Service
public class UserManagementImpl implements UserManagementI {

	// Todas la validaciones van en la capa de servicio.

	@Autowired
	private UserRepositoryI userRepo;

	@Override
	public User login(String user, String password) {

		return userRepo.findByUserAndPassword(user, password);
	}

	@Override
	public void addUser(User u) {

		userRepo.save(u);

	}

}
