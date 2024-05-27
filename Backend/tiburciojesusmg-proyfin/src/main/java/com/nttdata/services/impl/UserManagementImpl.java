package com.nttdata.services.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nttdata.persistence.dto.ChangePasswordDTO;
import com.nttdata.persistence.model.Role;
import com.nttdata.persistence.model.User;
import com.nttdata.persistence.model.UserBook;
import com.nttdata.persistence.repositories.UserBookRepositoryI;
import com.nttdata.persistence.repositories.UserRepositoryI;
import com.nttdata.services.UserManagementI;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserManagementImpl implements UserManagementI {

	// Todas la validaciones van en la capa de servicio.

	@Autowired
	private UserRepositoryI userRepo;

	@Autowired
	private UserBookRepositoryI userBookRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public List<User> getAllUsers() {
		return userRepo.findAll();
	}

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

	@Override
	public ResponseEntity<String> changePassword(Long userId, ChangePasswordDTO changePasswordDTO) {

		User user = userRepo.findById(userId)
				.orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado con id: " + userId));

		String newPassword = changePasswordDTO.getNewPassword();

		user.setPassword(passwordEncoder.encode(newPassword));
		userRepo.save(user);

		return ResponseEntity.ok("Contraseña actualizada correctamente.");
	}

	@Override
	@Transactional
	public void deleteUser(Long userId) {

		Optional<User> userOptional = userRepo.findById(userId);

		if (userOptional.isPresent()) {

			User user = userOptional.get();

			for (UserBook userBook : user.getUserBooks()) {
				userBookRepository.findByUserAndBook(user, userBook.getBook());

				userBookRepository.delete(userBook);
			}

			userRepo.deleteById(userId);
		} else {
			// Manejar el caso en el que el usuario no existe
			// Puedes lanzar una excepción, devolver un código de error, etc.
		}
	}

	@Override
	public User updateUser(Long id, String name, String lastName) {
		Optional<User> userOptional = userRepo.findById(id);

		if (userOptional.isPresent()) {
			User user = userOptional.get();

			if (name != null) {
				user.setName(name);
			}
			if (lastName != null) {
				user.setLastName(lastName);
			}

			return userRepo.save(user);
		} else {
			// Si no se encuentra el usuario con el ID proporcionado, puedes manejarlo de
			// acuerdo a tu lógica de negocio.
			throw new NoSuchElementException("No se encontró el usuario con ID: " + id);
		}
	}

}
