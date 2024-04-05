package com.nttdata.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.persistence.model.UserBook;
import com.nttdata.persistence.model.UserBookDTO;
import com.nttdata.services.UserBookImpl;

@RestController
@RequestMapping("/v1/usersBooks")
public class UserBookController {

	@Autowired
	private UserBookImpl userBookService;

	@GetMapping("/getUserDetails")
	public UserBook getUserBookDetails(@RequestParam Long userId, @RequestParam Long bookId) {
		return userBookService.getUserBookDetails(userId, bookId);
	}

	@PutMapping("/{id}")
	public UserBook updateUserBook(@PathVariable("id") Long id, @RequestBody UserBookDTO userBookDTO) {
		return userBookService.updateUserBook(id, userBookDTO.getStatus(), userBookDTO.getRate(),
				userBookDTO.getComment());
	}

}
