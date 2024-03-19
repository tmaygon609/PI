package com.nttdata.services;

import com.nttdata.persistence.model.User;

public interface UserManagementI {

	public User login(String user, String password);

	public void addUser(final User u);

	public User findUserById(Long idUser);

}
