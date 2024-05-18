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
 * Repositorio de T_USERBOOK
 */
@Repository
public interface UserBookRepositoryI extends JpaRepository<UserBook, Long> {

	public UserBook findByUserAndBook(User user, Book book);

//	@Query("SELECT ub.book FROM UserBook ub WHERE ub.book.title = :title AND ub.user.id = :userId")
//	List<Book> findByBookTitleAndUserId(String title, Long userId);

	@Query("SELECT ub.book FROM UserBook ub WHERE ub.user.id = :userId")
	List<Book> findBooksByUserId(@Param("userId") Long userId);

	UserBook findByUserIdAndBookId(Long userId, Long bookId);

	UserBook findByBookId(Long bookId);

	List<UserBook> findByBookIdAndUserId(Long bookId, Long userId);

}
