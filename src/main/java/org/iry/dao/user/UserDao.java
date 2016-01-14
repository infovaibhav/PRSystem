package org.iry.dao.user;

import java.util.List;

import org.iry.model.user.User;

public interface UserDao {

	void save(User user);
	
	User findById(long id);
	
	User findBySSO(String sso);
	
	List<User> findAllActiveUsers();
	
	List<User> findAllUsers();
	
}

