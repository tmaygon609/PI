package com.nttdata.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.persistence.model.User;

/**
 * Repositorio de T_USER
 */
@Repository
public interface UserRepositoryI extends JpaRepository<User, Long> {

	public User findByUserAndPassword(String user, String password);

	public User findByUser(String user);

}
