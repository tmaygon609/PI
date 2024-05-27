package com.nttdata.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.persistence.dto.ChangePasswordDTO;
import com.nttdata.persistence.dto.UserDTO;
import com.nttdata.persistence.model.User;
import com.nttdata.services.UserManagementI;

@RestController
@RequestMapping("/v1/users")
public class UserController {

	@Autowired
	private UserManagementI userService;

//	@PostMapping(path = "/login")
//	public User login(@RequestBody User u) {
//		String user = u.getUser();
//		String password = u.getPassword();
//
//		return userService.login(user, password);
//	}

	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		if (!users.isEmpty()) {
			return new ResponseEntity<>(users, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
		User updatedUser = userService.updateUser(id, userDTO.getName(), userDTO.getLastName());
		return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
	}

	@PostMapping("/saveUser")
	public void saveUser(final @RequestBody User u) {

		userService.addUser(u);
	}

	@PutMapping("/changePassword/{userId}")
	public ResponseEntity<String> changePassword(@PathVariable("userId") Long userId,
			@RequestBody ChangePasswordDTO changePasswordDTO) {
		return userService.changePassword(userId, changePasswordDTO);
	}

	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId) {
		try {
			userService.deleteUser(userId);
			return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error al eliminar el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
