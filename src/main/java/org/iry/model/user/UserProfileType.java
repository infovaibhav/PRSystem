package org.iry.model.user;

public enum UserProfileType {
	
	ADMIN ("ADMIN"),
	USER ("USER"),
	SUPERVISOR ("SUPERVISOR"),
	MANAGER ("MANAGER"),
	PURCHASE_USER ("PURCHASE_USER"),
	PURCHASE_SUPERVISOR ("PURCHASE_SUPERVISOR"),
	PURCHASE_MANAGER ("PURCHASE_MANAGER"),
	STORE_USER ("STORE_USER");
	
	String userProfileType;
	
	private UserProfileType(String userProfileType){
		this.userProfileType = userProfileType;
	}
	
	public String getUserProfileType(){
		return userProfileType;
	}
	
}
