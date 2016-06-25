/**
 * 
 */
package org.iry.service.user;

import java.util.List;

import org.iry.model.user.User;
import org.iry.model.user.UserProfileType;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author vaibhavp
 *
 */
public class UserDetails extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = -7480123492524719852L;
	
	private User user = null;
	private AllowedActions allowedActions = new AllowedActions();

	public UserDetails(User user, List<GrantedAuthority> authorities) {
		super(user.getSsoId(), user.getPassword(), 
				 user.getIsActive(), true, true, true, authorities);
		this.user = user;
		this.updateAllowedActions();
	}

	public User getUser() {
		return user;
	}
	public AllowedActions getAllowedActions() {
		return allowedActions;
	}
	
	private void updateAllowedActions() {
		allowedActions.createPr = true;
		for (GrantedAuthority authority : this.getAuthorities()) {
			String authorityName = authority.getAuthority();
			if( authorityName.equalsIgnoreCase(UserProfileType.ADMIN.getUserProfileType())) {
				allowedActions.admin = true;
			} else if( authorityName.equalsIgnoreCase(UserProfileType.USER.getUserProfileType())) {
				allowedActions.cancelPr = true;
			} else if( authorityName.equalsIgnoreCase(UserProfileType.SUPERVISOR.getUserProfileType())) {
				allowedActions.authorizePr = true;
				allowedActions.reopenPr = true;
				allowedActions.cancelPr = true;
				allowedActions.editPrRemark = true;
			} else if( authorityName.equalsIgnoreCase(UserProfileType.MANAGER.getUserProfileType())) {
				allowedActions.authorizePr = true;
				allowedActions.approvePr = true;
				allowedActions.reopenPr = true;
				allowedActions.cancelPr = true;
				allowedActions.editPrRemark = true;
			} else if( authorityName.equalsIgnoreCase(UserProfileType.PURCHASE_USER.getUserProfileType())) {
				allowedActions.createPo = true;
				allowedActions.acknowledgePr = true;
				allowedActions.updateRequestQuote = true;
				allowedActions.updateReceiveQuote = true;
				allowedActions.updateFinalizeQuote = true;
				allowedActions.updatePoCreated = true;
				allowedActions.editPrItemsRemark = true;
			} else if( authorityName.equalsIgnoreCase(UserProfileType.PURCHASE_SUPERVISOR.getUserProfileType())) {
				allowedActions.approvePo = true;
				allowedActions.acknowledgePr = true;
				allowedActions.updateRequestQuote = true;
				allowedActions.updateReceiveQuote = true;
				allowedActions.updateFinalizeQuote = true;
				allowedActions.updatePoCreated = true;
				allowedActions.editPrItemsRemark = true;
			} else if( authorityName.equalsIgnoreCase(UserProfileType.PURCHASE_MANAGER.getUserProfileType())) {
				allowedActions.approvePo = true;
				allowedActions.acknowledgePr = true;
				allowedActions.updateRequestQuote = true;
				allowedActions.updateReceiveQuote = true;
				allowedActions.updateFinalizeQuote = true;
				allowedActions.updatePoCreated = true;
				allowedActions.editPrItemsRemark = true;
			} else if( authorityName.equalsIgnoreCase(UserProfileType.STORE_USER.getUserProfileType())) {
				allowedActions.updateReceiveMaterial = true;
			}
		}
	}
	
}
