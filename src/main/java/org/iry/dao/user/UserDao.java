package org.iry.dao.user;

import java.util.List;
import java.util.Set;

import org.iry.dto.SearchCriteria;
import org.iry.model.user.User;

public interface UserDao {

	void save(User user);
	
	User findById(long id);
	
	String getFullNameById(long id);
	
	User findBySSO(String sso);
	
	List<User> findAllActiveUsers();
	
	List<User> findUsers(SearchCriteria searchCriteria);

	List<String> getUserEmailsByIds(Set<Long> userIds);

	List<String> getUserEmailsByTypes(Set<String> userProfileTypes);

	List<Long> getAllSubordinateUserIds(Long userId);

}

