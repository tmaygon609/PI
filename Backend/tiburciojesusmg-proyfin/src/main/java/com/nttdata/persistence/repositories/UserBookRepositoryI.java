package com.nttdata.persistence.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.nttdata.persistence.model.UserBook;

/**
 * Repositorio de T_USER_BOOK
 */
@Repository
public interface UserBookRepositoryI extends JpaRepository<UserBook, Long> {

	@Query("SELECT ub FROM UserBook ub WHERE ub.user.id = :id")
	List<UserBook> findByUserId(@Param("id") Long userId);

}
