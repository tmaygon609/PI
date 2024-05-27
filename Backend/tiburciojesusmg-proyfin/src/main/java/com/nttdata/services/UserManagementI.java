package com.nttdata.services;

import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.nttdata.persistence.dto.ChangePasswordDTO;
import com.nttdata.persistence.model.User;

/**
 * Servicio de gestión de usuarios.
 */
@ComponentScan
public interface UserManagementI {

	/**
	 * Provee el servicio de detalles del usuario.
	 *
	 * @return El servicio de detalles del usuario.
	 */
	UserDetailsService userDetailsService();

	/**
	 * Inicia sesión de un usuario.
	 *
	 * @param user     Nombre de usuario.
	 * @param password Contraseña.
	 * @return El usuario autenticado.
	 */
	User login(String user, String password);

	/**
	 * Añade un nuevo usuario.
	 *
	 * @param user El usuario a añadir.
	 */
	void addUser(final User u);

	/**
	 * Obtiene un usuario por su nombre de usuario.
	 *
	 * @param user Nombre de usuario.
	 * @return El usuario encontrado.
	 */
	User getUserByUser(String user);

	/**
	 * Cambia la contraseña de un usuario.
	 *
	 * @param userId            El ID del usuario.
	 * @param changePasswordDTO Los datos de cambio de contraseña.
	 * @return Respuesta de la operación.
	 */
	ResponseEntity<String> changePassword(Long userId, ChangePasswordDTO changePasswordDTO);

	/**
	 * Elimina un usuario.
	 *
	 * @param userId El ID del usuario a eliminar.
	 */
	void deleteUser(Long userId);

	/**
	 * Obtiene todos los usuarios.
	 *
	 * @return Lista de todos los usuarios.
	 */
	List<User> getAllUsers();

	/**
	 * Actualiza un usuario.
	 *
	 * @param id       El ID del usuario.
	 * @param name     El nuevo nombre.
	 * @param lastName El nuevo apellido.
	 * @return El usuario actualizado.
	 */
	User updateUser(Long id, String name, String lastName);

}
