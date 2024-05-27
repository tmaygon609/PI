package com.nttdata.services;

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

}
