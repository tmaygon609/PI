package com.nttdata.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.persistence.model.User;

/**
 * Repositorio para la entidad User.
 */
@Repository
public interface UserRepositoryI extends JpaRepository<User, Long> {

	/**
	 * Encuentra un usuario por su nombre de usuario y contraseña.
	 * 
	 * @param user     Nombre de usuario.
	 * @param password Contraseña.
	 * @return Usuario encontrado.
	 */
	User findByUserAndPassword(String user, String password);

	/**
	 * Encuentra un usuario por su nombre de usuario.
	 * 
	 * @param user Nombre de usuario.
	 * @return Usuario encontrado.
	 */
	User findByUser(String user);

}
