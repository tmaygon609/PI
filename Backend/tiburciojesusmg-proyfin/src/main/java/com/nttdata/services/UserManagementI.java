package com.nttdata.services;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.nttdata.persistence.model.User;

@ComponentScan
public interface UserManagementI {

	UserDetailsService userDetailsService();

	public User login(String user, String password);

	public void addUser(final User u);

	public User getUserByUser(String user);

}
