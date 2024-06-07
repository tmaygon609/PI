package com.nttdata.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.dao.request.SignUpRequest;
import com.nttdata.dao.request.SigninRequest;
import com.nttdata.dao.response.JwtAuthenticationResponse;
import com.nttdata.persistence.model.User;
import com.nttdata.services.AuthenticationServiceI;
import com.nttdata.services.UserManagementI;

import lombok.RequiredArgsConstructor;

/**
 * Controlador para manejar las solicitudes de autenticación.
 */
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticationServiceI authenticationService;

	private final UserManagementI userService;

	/**
	 * Método para registrar un nuevo usuario.
	 *
	 * @param request SignUpRequest objeto de solicitud de registro.
	 * @return ResponseEntity que contiene la respuesta de autenticación JWT.
	 */
	@PostMapping("/signup")
	public ResponseEntity<JwtAuthenticationResponse> signup(@RequestBody SignUpRequest request) {
		return ResponseEntity.ok(authenticationService.signup(request));
	}

	/**
	 * Método para iniciar sesión con un usuario existente.
	 *
	 * @param request SigninRequest objeto de solicitud de inicio de sesión.
	 * @return ResponseEntity que contiene la respuesta de autenticación JWT.
	 */
	@PostMapping("/signin")
	public ResponseEntity<JwtAuthenticationResponse> signin(@RequestBody SigninRequest request) {
		JwtAuthenticationResponse response = authenticationService.signin(request);
		if (response != null && response.getAccessToken() != null) {
			String username = request.getUser();
			User user = userService.getUserByUser(username); // Obtener el usuario por su nombre de usuario
			if (user != null) {
				response.setUser(user); // Establecer el usuario en la respuesta
			}
		}
		return ResponseEntity.ok(response);
	}

}
