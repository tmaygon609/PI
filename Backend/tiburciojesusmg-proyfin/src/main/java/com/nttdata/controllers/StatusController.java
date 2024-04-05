package com.nttdata.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.persistence.model.Status;
import com.nttdata.services.StatusService;

@RestController
@RequestMapping("/v1/status")
public class StatusController {

	@Autowired
	private StatusService statusService;

	@GetMapping
	public ResponseEntity<List<Status>> getAllStatusNames() {
		List<Status> statuses = statusService.getAllStatusNames();
		return new ResponseEntity<>(statuses, HttpStatus.OK);
	}

}
