package com.nttdata.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.exceptions.UserNotFoundException;
import com.nttdata.persistence.dto.ChangePasswordDTO;
import com.nttdata.persistence.dto.UserDTO;
import com.nttdata.persistence.model.User;
import com.nttdata.services.UserManagementI;

/**
 * Controlador para manejar las solicitudes relacionadas con los usuarios.
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {

	@Autowired
	private UserManagementI userService;

	/**
	 * Obtiene todos los usuarios.
	 *
	 * @return ResponseEntity con la lista de todos los usuarios encontrados.
	 */
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAllUsers();
		if (!users.isEmpty()) {
			return new ResponseEntity<>(users, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Actualiza un usuario existente.
	 *
	 * @param id      ID del usuario a actualizar.
	 * @param userDTO Objeto UserDTO con los nuevos detalles del usuario.
	 * @return ResponseEntity con el usuario actualizado.
	 */
	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) {
		User updatedUser = userService.updateUser(id, userDTO.getName(), userDTO.getLastName());
		return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
	}

	/**
	 * Guarda un nuevo usuario.
	 *
	 * @param u Objeto User que representa el nuevo usuario a guardar.
	 */
	@PostMapping("/saveUser")
	public void saveUser(final @RequestBody User u) {

		userService.addUser(u);
	}

	/**
	 * Cambia la contraseña de un usuario.
	 *
	 * @param userId            ID del usuario cuya contraseña se va a cambiar.
	 * @param changePasswordDTO Objeto ChangePasswordDTO con la nueva contraseña.
	 * @return ResponseEntity con un mensaje de éxito o error.
	 */
	@PutMapping("/changePassword/{userId}")
	public ResponseEntity<String> changePassword(@PathVariable("userId") Long userId,
			@RequestBody ChangePasswordDTO changePasswordDTO) {
		return userService.changePassword(userId, changePasswordDTO);
	}

	/**
	 * Elimina un usuario por su ID.
	 *
	 * @param userId ID del usuario a eliminar.
	 * @return ResponseEntity con un mensaje de éxito o error.
	 */
	@DeleteMapping("/delete/{userId}")
	public ResponseEntity<String> deleteUser(@PathVariable("userId") Long userId) {
		try {
			userService.deleteUser(userId);
			return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error al eliminar el usuario", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * Maneja la excepción cuando no se encuentra un usuario.
	 *
	 * @param ex Excepción de usuario no encontrado.
	 * @return ResponseEntity con un mensaje de error.
	 */
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	/**
	 * Maneja excepciones generales.
	 *
	 * @param ex Excepción general.
	 * @return ResponseEntity con un mensaje de error interno.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleGeneralException(Exception ex) {
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrió un error interno");
	}
}
