package org.iry.dao.user;

import java.util.List;

import org.iry.dto.SearchCriteria;
import org.iry.dto.user.UserDto;
import org.iry.model.user.User;

public interface UserDao {

	void save(User user);
	
	User findById(long id);
	
	User findBySSO(String sso);
	
	List<UserDto> findAllActiveUsers();
	
	List<User> findUsers(SearchCriteria searchCriteria);
	
}

