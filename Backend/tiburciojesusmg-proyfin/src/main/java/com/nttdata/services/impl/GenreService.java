package com.nttdata.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.exceptions.EntityNotFoundException;
import com.nttdata.persistence.model.Genre;
import com.nttdata.persistence.repositories.GenreRepositoryI;

/**
 * Servicio para operaciones relacionadas con los géneros.
 */
@Service
public class GenreService {

	@Autowired
	private GenreRepositoryI genreRepository;

	/**
	 * Obtiene todos los nombres de los géneros.
	 *
	 * @return Una lista de objetos Genre que representan los géneros.
	 * @throws EntityNotFoundException Si no se encuentran géneros en la base de
	 *                                 datos.
	 */
	public List<Genre> getAllGenresNames() {

		List<Genre> genres = genreRepository.findAll();
		if (genres.isEmpty()) {
			throw new EntityNotFoundException("No se encontraron géneros");
		}
		return genres;
	}

}
