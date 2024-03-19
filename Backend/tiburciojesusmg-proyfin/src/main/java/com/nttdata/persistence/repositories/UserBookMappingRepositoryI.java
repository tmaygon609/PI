package com.nttdata.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.persistence.model.UserBookMapping;

/**
 * Repositorio de T_USER_BOOK
 */
@Repository
public interface UserBookMappingRepositoryI extends JpaRepository<UserBookMapping, Long> {

}
