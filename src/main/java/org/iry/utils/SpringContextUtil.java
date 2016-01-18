/**
 * 
 */
package org.iry.utils;

import java.util.HashSet;
import java.util.Set;

import org.iry.model.user.User;
import org.iry.service.user.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author vaibhavp
 *
 */
public class SpringContextUtil {
	
	public static String getPrincipal(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			userName = ((UserDetails)principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
	
	public static String getFullName(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			User user = ((UserDetails)principal).getUser();
			userName = user.getFullName();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
	
	public static User getUser(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return ((UserDetails)principal).getUser();
		}
		return null;
	}
	
	public static UserDetails getUserDetails(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			return (UserDetails)principal;
		}
		return null;
	}
	
	public static Long getUserId(){
		Long userId = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			User user = ((UserDetails)principal).getUser();
			userId = user.getId();
		}
		return userId;
	}
	
	public static Set<String> getPrivileges(){
		Set<String> privileges = new HashSet<String>();
		for (GrantedAuthority auth : SecurityContextHolder.getContext().getAuthentication().getAuthorities()) {
			privileges.add(auth.getAuthority());
        }
		return privileges;
	}

}
