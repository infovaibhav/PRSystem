/**
 * 
 */
package org.iry.service.user;

import java.util.List;

import org.iry.model.user.User;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author vaibhavp
 *
 */
public class UserDetails extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = -7480123492524719852L;
	
	private User user = null;

	public UserDetails(User user, List<GrantedAuthority> authorities) {
		super(user.getSsoId(), user.getPassword(), 
				 user.getIsActive(), true, true, true, authorities);
		this.user = user;
	}

	public User getUser() {
		return user;
	}
	
	public class AllowedActions {
		boolean admin = false;
		boolean createPr = false;
		boolean authorizePr = false;
		boolean approvePr = false;
		boolean acknowledgePr = false;
		boolean cancelPr = false;
		boolean updateRequestQuote = false;
		boolean updateReceiveQuote = false;
		boolean updateFinalizeQuote = false;
		boolean createPo = false;
		boolean approvePo = false;
		boolean updateReceiveMaterial = false;
	}
	
	private void updateAllowedActions() {
		
	}
	
}
