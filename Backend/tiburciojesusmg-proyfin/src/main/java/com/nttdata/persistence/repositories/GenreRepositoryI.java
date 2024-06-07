package com.nttdata.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.persistence.model.Genre;

/**
 * Repositorio para la entidad Genre.
 */
@Repository
public interface GenreRepositoryI extends JpaRepository<Genre, Long> {

}
