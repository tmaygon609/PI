package com.nttdata.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.persistence.model.Status;
import com.nttdata.persistence.repositories.StatusRepositoryI;

@Service
public class StatusService {

	@Autowired
	private StatusRepositoryI statusRepository;

	public List<Status> getAllStatusNames() {

		return statusRepository.findAll();
	}

}
