package com.nttdata.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.persistence.model.Book;
import com.nttdata.persistence.repositories.BookRepositoryI;

@Service
public class BuildingManagementImpl implements BuildingManagementI {

	// Todas la validaciones van en la capa de servicio.
	
	@Autowired
	private BookRepositoryI bookRepo;
	
	@Override
	public void addBook(Book b) {
		
		bookRepo.save(b);
		
	}
	
	@Override
	public void deleteBook(Book b) {
		 
		bookRepo.delete(b);
		
	}
	
	@Override
	public List<Book> searchAllBooks() {
		
		return bookRepo.findAll();
	}

	@Override
	public List<Book> searchByTitle(String title) {
		
		return bookRepo.searchByTitle(title);
	}

	

}
