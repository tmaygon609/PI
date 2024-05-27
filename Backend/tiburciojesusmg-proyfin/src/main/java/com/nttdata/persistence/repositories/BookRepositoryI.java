package com.nttdata.persistence.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.persistence.model.Book;

/**
 * Repositorio para la entidad Book.
 */
@Repository
public interface BookRepositoryI extends JpaRepository<Book, Long> {

	/**
	 * Encuentra libros que contienen el título dado.
	 * 
	 * @param title Título del libro.
	 * @return Lista de libros que contienen el título.
	 */
	List<Book> searchByTitleContaining(final String title);

	/**
	 * Encuentra libros por género con paginación.
	 * 
	 * @param genre    Género del libro.
	 * @param pageable Información de paginación.
	 * @return Página de libros del género especificado.
	 */
	Page<Book> searchByGenre(String genre, Pageable pageable);

}
