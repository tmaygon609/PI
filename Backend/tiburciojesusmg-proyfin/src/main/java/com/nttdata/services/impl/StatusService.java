package com.nttdata.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.exceptions.EntityNotFoundException;
import com.nttdata.persistence.model.Status;
import com.nttdata.persistence.repositories.StatusRepositoryI;

/**
 * Servicio para operaciones relacionadas con los estados.
 */
@Service
public class StatusService {

	@Autowired
	private StatusRepositoryI statusRepository;

	/**
	 * Obtiene todos los nombres de los estados.
	 *
	 * @return Una lista de objetos Status que representan los estados.
	 * @throws EntityNotFoundException Si no se encuentran estados en la base de
	 *                                 datos.
	 */
	public List<Status> getAllStatusNames() {

		List<Status> statuses = statusRepository.findAll();

		if (statuses.isEmpty()) {
			throw new EntityNotFoundException("No se encontraron estados");
		}
		return statuses;
	}

}
