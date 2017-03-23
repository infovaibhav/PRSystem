package org.iry.service.user;

import java.util.List;

import org.iry.model.user.UserProfile;

public interface UserProfileService {

	List<UserProfile> findAll();
	
	UserProfile findByType(String type);
	
	UserProfile findById(int id);
}
