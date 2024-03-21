package com.nttdata.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.persistence.model.User;
import com.nttdata.services.UserManagementI;

@RestController
@RequestMapping("/v1/users")
public class UserController {

	@Autowired
	private UserManagementI userService;

	@GetMapping(path = "/login")
	public User login(@RequestParam("user") String user, @RequestParam("password") String password) {

		return userService.login(user, password);
	}

	@PostMapping("/saveUser")
	public void saveUser(final @RequestBody User u) {

		userService.addUser(u);
	}

}
