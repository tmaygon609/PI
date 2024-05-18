package com.nttdata.services;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.nttdata.persistence.dto.ChangePasswordDTO;
import com.nttdata.persistence.model.User;

@ComponentScan
public interface UserManagementI {

	UserDetailsService userDetailsService();

	public User login(String user, String password);

	public void addUser(final User u);

	public User getUserByUser(String user);

	public ResponseEntity<String> changePassword(Long userId, ChangePasswordDTO changePasswordDTO);

	public void deleteUser(Long userId);

}
