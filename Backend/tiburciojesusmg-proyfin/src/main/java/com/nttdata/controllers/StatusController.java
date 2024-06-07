package com.nttdata.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.persistence.model.Status;
import com.nttdata.services.impl.StatusService;

/**
 * Controlador para manejar las solicitudes relacionadas con los estados de los
 * libros.
 */
@RestController
@RequestMapping("/v1/status")
public class StatusController {

	@Autowired
	private StatusService statusService;

	/**
	 * Obtiene todos los nombres de los estados de los libros.
	 *
	 * @return ResponseEntity con la lista de estados de los libros
	 */
	@GetMapping
	public ResponseEntity<List<Status>> getAllStatusNames() {

		List<Status> statuses = statusService.getAllStatusNames();
		if (statuses.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(statuses, HttpStatus.OK);
	}

}
