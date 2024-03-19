package com.nttdata.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nttdata.persistence.model.Book;

/**
 * Repositorio de T_BOOK
 */
@Repository
public interface BookRepositoryI extends JpaRepository<Book, Long> {

	public List<Book> searchByTitle(final String title);

	@Query("SELECT b FROM Book b JOIN b.users u WHERE u.id = :userId")
	List<Book> findBooksByUserId(@Param("userId") Long userId);

}
