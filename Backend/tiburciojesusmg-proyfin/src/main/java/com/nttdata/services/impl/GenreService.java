package com.nttdata.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.persistence.model.Genre;
import com.nttdata.persistence.repositories.GenreRepositoryI;

@Service
public class GenreService {

	@Autowired
	private GenreRepositoryI genreRepository;

	public List<Genre> getAllGenresNames() {

		return genreRepository.findAll();
	}

}
