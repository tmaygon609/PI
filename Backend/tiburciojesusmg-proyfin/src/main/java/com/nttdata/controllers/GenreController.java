package com.nttdata.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.persistence.model.Genre;
import com.nttdata.services.impl.GenreService;

/**
 * Controlador para manejar las solicitudes relacionadas con los géneros
 * literarios.
 */
@RestController
@RequestMapping("/v1/genres")
@PreAuthorize("hasRole('USER')")
public class GenreController {

	@Autowired
	private GenreService genreService;

	/**
	 * Obtiene todos los nombres de géneros literarios.
	 *
	 * @return ResponseEntity con la lista de géneros literarios
	 */
	@GetMapping
	public ResponseEntity<List<Genre>> getAllGenreNames() {

		List<Genre> genres = genreService.getAllGenresNames();

		if (genres.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(genres, HttpStatus.OK);
	}

}
