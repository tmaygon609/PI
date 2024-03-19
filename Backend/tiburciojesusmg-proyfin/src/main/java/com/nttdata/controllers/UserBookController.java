package com.nttdata.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.persistence.model.UserBook;
import com.nttdata.services.UserBookServiceI;

@RestController
@RequestMapping("/userBook")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserBookController {

	@Autowired
	private UserBookServiceI userBookService;

	@GetMapping("/books")
	public List<UserBook> getBooksByUser(@RequestParam Long userId) {
		return userBookService.getBooksByUser(userId);
	}

}
