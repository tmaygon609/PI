package com.nttdata.services;

import java.util.List;

import com.nttdata.persistence.model.UserBook;

/**
 * Servicio de relación usuario-libro.
 */
public interface UserBookI {

	/**
	 * Obtiene los detalles de la relación usuario-libro.
	 *
	 * @param userId El ID del usuario.
	 * @param bookId El ID del libro.
	 * @return La relación usuario-libro.
	 */
	UserBook getUserBookDetails(Long userId, Long bookId);

	/**
	 * Actualiza la relación usuario-libro.
	 *
	 * @param id      El ID de la relación.
	 * @param status  El nuevo estado.
	 * @param rate    La nueva calificación.
	 * @param comment El nuevo comentario.
	 * @return La relación usuario-libro actualizada.
	 */
	UserBook updateUserBook(Long id, String status, String rate, String comment);

	/**
	 * Busca los libros de usuario por ID de libro y ID de usuario.
	 *
	 * @param bookId ID del libro.
	 * @param userId ID del usuario.
	 * @return La lista de libros de usuario encontrados.
	 */
	List<UserBook> searchBooksByBookIdAndUserId(Long bookId, Long userId);

	/**
	 * Elimina un libro de usuario por su ID.
	 *
	 * @param id ID del libro de usuario.
	 */
	void deleteBook(Long id);

}
