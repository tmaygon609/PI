package com.nttdata.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.persistence.model.Book;

/**
 * Repositorio de T_BOOK
 */
@Repository
public interface BookRepositoryI extends JpaRepository<Book, Long> {

	public List<Book> searchByTitle(final String title);

	public List<Book> searchByGenre(final String genre);

//	@Query("SELECT b FROM Book b JOIN b.users u WHERE b.title = :title AND u.id = :userId")
//	List<Book> findByTitleAndUserId(String title, Long userId);
//
//	@Query("SELECT b FROM Book b JOIN b.users u WHERE u.id = :userId")
//	List<Book> findBooksByUserId(@Param("userId") Long userId);

}
