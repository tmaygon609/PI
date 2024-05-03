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

	@Override
	public void changePassword(Long userId, String newPassword) {
		User user = userRepo.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado con id: " + userId));

		// Codificar la nueva contraseña antes de guardarla
		String encodedPassword = passwordEncoder.encode(newPassword);
		user.setPassword(encodedPassword);

		userRepo.save(user);
	}

}
