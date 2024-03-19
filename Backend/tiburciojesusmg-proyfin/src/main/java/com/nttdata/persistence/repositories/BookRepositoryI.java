package com.nttdata.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.persistence.model.Book;

/**
 * Repositorio de T_BOOK
 */
@Repository
public interface BookRepositoryI extends JpaRepository<Book, Long>{
	
	public List<Book> searchByTitle(final String title);

}
