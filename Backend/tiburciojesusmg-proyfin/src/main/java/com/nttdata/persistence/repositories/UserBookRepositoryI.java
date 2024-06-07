package com.nttdata.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nttdata.persistence.model.Book;
import com.nttdata.persistence.model.User;
import com.nttdata.persistence.model.UserBook;

/**
 * Repositorio para la entidad UserBook.
 */
@Repository
public interface UserBookRepositoryI extends JpaRepository<UserBook, Long> {

	/**
	 * Encuentra la relación UserBook por usuario y libro.
	 * 
	 * @param user Usuario.
	 * @param book Libro.
	 * @return Relación UserBook.
	 */
	UserBook findByUserAndBook(User user, Book book);

	/**
	 * Encuentra libros por el ID de usuario.
	 * 
	 * @param userId ID del usuario.
	 * @return Lista de libros asociados al usuario.
	 */
	@Query("SELECT ub.book FROM UserBook ub WHERE ub.user.id = :userId")
	List<Book> findBooksByUserId(@Param("userId") Long userId);

	/**
	 * Encuentra la relación UserBook por ID de usuario y ID de libro.
	 * 
	 * @param userId ID del usuario.
	 * @param bookId ID del libro.
	 * @return Relación UserBook.
	 */
	UserBook findByUserIdAndBookId(Long userId, Long bookId);

	/**
	 * Encuentra la relación UserBook por ID de libro.
	 * 
	 * @param bookId ID del libro.
	 * @return Relación UserBook.
	 */
	UserBook findByBookId(Long bookId);

	/**
	 * Encuentra la relación UserBook por ID de libro y ID de usuario.
	 * 
	 * @param bookId ID del libro.
	 * @param userId ID del usuario.
	 * @return Lista de relaciones UserBook.
	 */
	List<UserBook> findByBookIdAndUserId(Long bookId, Long userId);

}
