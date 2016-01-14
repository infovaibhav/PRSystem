package org.iry.service.user;

import java.util.List;

import org.iry.model.user.User;

public interface UserService {

	void save(User user);
	
	User findById(long id);
	
	User findBySso(String sso);

	List<User> findAllActiveUsers();

	List<User> findAllUsers();
	
}