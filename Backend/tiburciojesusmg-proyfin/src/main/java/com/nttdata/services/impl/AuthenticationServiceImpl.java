package com.nttdata.services.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.nttdata.dao.request.SignUpRequest;
import com.nttdata.dao.request.SigninRequest;
import com.nttdata.dao.response.JwtAuthenticationResponse;
import com.nttdata.exceptions.AuthenticationException;
import com.nttdata.exceptions.UserAlreadyExistsException;
import com.nttdata.exceptions.UserNotFoundException;
import com.nttdata.persistence.model.Role;
import com.nttdata.persistence.model.User;
import com.nttdata.persistence.repositories.UserRepositoryI;
import com.nttdata.services.AuthenticationServiceI;
import com.nttdata.services.JwtServiceI;

import lombok.RequiredArgsConstructor;

/**
 * Implementación del servicio de autenticación.
 */
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationServiceI {

	private final UserRepositoryI userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtServiceI jwtService;
	private final AuthenticationManager authenticationManager;

	@Override
	public JwtAuthenticationResponse signup(SignUpRequest request) {
		// Se utiliza var como variable local y asi no especificar el tipo
		// explicitamente.

		var existingUser = userRepository.findByUser(request.getUser());
		if (existingUser != null) {
			throw new UserAlreadyExistsException("El usuario ya existe en el sistema");
		}

		var user = User.builder().name(request.getName()).lastName(request.getLastName()).user(request.getUser())
				.password(passwordEncoder.encode(request.getPassword())).role(Role.USER).build();
		userRepository.save(user);
		var jwt = jwtService.generateToken(user);
		return JwtAuthenticationResponse.builder().accessToken(jwt).build();
	}

	@Override
	public JwtAuthenticationResponse signin(SigninRequest request) {

		try {
			authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(request.getUser(), request.getPassword()));
		} catch (AuthenticationException ex) {
			throw new AuthenticationException("Credenciales de inicio de sesión incorrectas");
		}

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getUser(), request.getPassword()));
		var user = userRepository.findByUser(request.getUser());

		if (user == null) {
			throw new UserNotFoundException(request.getUser());
		}

		var jwt = jwtService.generateToken(user);
		return JwtAuthenticationResponse.builder().accessToken(jwt).user(user).build();
	}

}
