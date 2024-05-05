package com.nttdata.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nttdata.persistence.model.Role;
import com.nttdata.persistence.model.User;
import com.nttdata.persistence.repositories.UserRepositoryI;
import com.nttdata.services.UserManagementI;

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
	public User getUserByUser(String user) {

		return userRepo.findByUser(user);
	}

	@Override
	public void addUser(User u) {

		u.setPassword(passwordEncoder.encode(u.getPassword()));
		u.setRole(Role.USER);

		userRepo.save(u);

	}

	@Override
	public UserDetailsService userDetailsService() {

		return new UserDetailsService() {

			@Override
			public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

				return userRepo.findByUser(username);
			}

		};
	}

}
