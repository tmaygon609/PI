package com.nttdata.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.persistence.dto.ChangePasswordRequestDTO;
import com.nttdata.persistence.model.User;
import com.nttdata.services.UserManagementI;

@RestController
@RequestMapping("/v1/users")
public class UserController {

	@Autowired
	private UserManagementI userService;

	@PostMapping(path = "/login")
	public User login(@RequestBody User u) {
		String user = u.getUser();
		String password = u.getPassword();

		return userService.login(user, password);
	}

	@PostMapping("/saveUser")
	public void saveUser(final @RequestBody User u) {

		userService.addUser(u);
	}

	@PutMapping("/changePassword")
	public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequestDTO requestDTO) {
		try {
			userService.changePassword(requestDTO.getUserId(), requestDTO.getNewPassword());
			return ResponseEntity.ok("Contraseña cambiada exitosamente");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al cambiar la contraseña");
		}
	}

}
