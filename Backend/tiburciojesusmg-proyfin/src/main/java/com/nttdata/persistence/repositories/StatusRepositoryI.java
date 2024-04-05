package com.nttdata.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.persistence.model.Status;

/**
 * Repositorio de T_STATUS
 */
@Repository
public interface StatusRepositoryI extends JpaRepository<Status, Long> {

}
