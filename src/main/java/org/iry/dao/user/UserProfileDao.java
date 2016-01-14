package org.iry.dao.user;

import java.util.List;

import org.iry.model.user.UserProfile;

public interface UserProfileDao {

	List<UserProfile> findAll();
	
	UserProfile findByType(String type);
	
	UserProfile findById(int id);
}
