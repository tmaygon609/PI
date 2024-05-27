package com.nttdata.services;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.nttdata.persistence.model.Book;
import com.nttdata.persistence.model.UserBook;

/**
 * Servicio de gestión de libros.
 */
public interface BookManagementI {

	/**
	 * Añade un nuevo libro.
	 *
	 * @param book El libro a añadir.
	 * @return El libro añadido.
	 */
	Book addBook(final Book b);

	/**
	 * Busca todos los libros con paginación.
	 *
	 * @param pageable Información de paginación.
	 * @return Página de libros.
	 */
	Page<Book> searchAllBooks(Pageable pageable);

	/**
	 * Obtiene todos los libros.
	 *
	 * @return Lista de todos los libros.
	 */
	List<Book> getAllBooks();

	/**
	 * Busca libros por género con paginación.
	 *
	 * @param genre    El género del libro.
	 * @param pageable Información de paginación.
	 * @return Página de libros del género especificado.
	 */
	Page<Book> searchByGenre(String genre, Pageable pageable);

	/**
	 * Busca libros por título.
	 *
	 * @param title El título del libro.
	 * @return Lista de libros que coinciden con el título.
	 */
	List<Book> searchByTitle(String title);

	/**
	 * Obtiene los títulos de libros de un usuario internamente para la llamada a
	 * OpenAI.
	 *
	 * @param userId El ID del usuario.
	 * @return Lista de títulos de libros.
	 */
	List<String> getBookTitlesOfUser(Long userId);

//	/**
//	 * Encuentra libros por ID de usuario.
//	 *
//	 * @param userId El ID del usuario.
//	 * @return Lista de libros asociados al usuario.
//	 */
//	List<Book> findBooksByUserId(final Long userId);

	/**
	 * Añade un libro a un usuario.
	 *
	 * @param userBook La relación usuario-libro.
	 */
	void addBookToUser(UserBook userBook);

	/**
	 * Obtiene libros por ID de usuario.
	 *
	 * @param userId El ID del usuario.
	 * @return Conjunto de libros del usuario.
	 */
	Set<Book> getBooksByUserId(Long userId);

	/**
	 * Actualiza un libro.
	 *
	 * @param id     El ID del libro a actualizar.
	 * @param title  El nuevo título.
	 * @param author El nuevo autor.
	 * @return El libro actualizado.
	 */
	Book updateBook(Long id, String title, String author);

	/**
	 * Elimina un libro de la relación UserBook por su ID.
	 *
	 * @param id El ID del libro a eliminar.
	 */
	void deleteBook(final Long id);

	/**
	 * Elimina un libro de la bbdd.
	 *
	 * @param id El ID del libro a eliminar.
	 */
	void removeBook(Long id);

//	public List<Book> searchByTitleAndUser(String title, Long userId);

//	public List<Book> searchAllBooks();

//	public List<Book> searchByGenre(String genre);

//	public Book addBookWithImage(Book book, MultipartFile image) throws IOException;

}
