/**
 * 
 */
package org.iry.utils;

import org.iry.model.user.User;
import org.iry.service.user.UserDetails;
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
	
	public static String getName(){
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			User user = ((UserDetails)principal).getUser();
			userName = user.getFirstName() + " " + user.getLastName();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

}
