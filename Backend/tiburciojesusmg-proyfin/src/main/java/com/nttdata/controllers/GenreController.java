package com.nttdata.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.persistence.model.Genre;
import com.nttdata.services.GenreService;

@RestController
@RequestMapping("/v1/genres")
public class GenreController {

	@Autowired
	private GenreService genreService;

	@GetMapping
	public ResponseEntity<List<Genre>> getAllGenreNames() {
		List<Genre> genres = genreService.getAllGenresNames();
		return new ResponseEntity<>(genres, HttpStatus.OK);
	}

}
