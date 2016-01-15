package org.iry.service.user;

import java.util.List;

import org.iry.dto.SearchCriteria;
import org.iry.dto.user.UserDto;
import org.iry.model.user.User;

public interface UserService {

	void save(User user);
	
	User findById(long id);
	
	User findBySso(String sso);

	List<UserDto> findAllActiveUsers();

	List<UserDto> findUsers(SearchCriteria searchCriteria);
	
}