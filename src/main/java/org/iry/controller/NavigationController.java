package org.iry.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.iry.model.pr.PurchaseRequisition;
import org.iry.model.pr.PurchaseRequisitionItems;
import org.iry.model.user.User;
import org.iry.model.user.UserProfile;
import org.iry.model.user.UserProfileType;
import org.iry.service.user.UserProfileService;
import org.iry.service.user.UserService;
import org.iry.utils.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class NavigationController {

	private static final Logger log = Logger.getLogger(NavigationController.class);
	
	@Autowired
	UserProfileService userProfileService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = { "/", "/home" }, method = RequestMethod.GET)
	public String homePage(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		Set<String> privileges = SpringContextUtil.getPrivileges();
		if( privileges.contains(UserProfileType.MANAGER.getUserProfileType())
				|| privileges.contains(UserProfileType.PURCHASE_MANAGER.getUserProfileType())
				|| privileges.contains(UserProfileType.PURCHASE_SUPERVISOR.getUserProfileType())
				|| privileges.contains(UserProfileType.PURCHASE_USER.getUserProfileType()) ) {
			return searchPurchaseRequisitions(model);
		} else if( privileges.contains(UserProfileType.ADMIN.getUserProfileType()) ) {
			return viewUsers(model);
		} else {
			return myPurchaseRequisitions(request, response, model);
		}
	}

	@RequestMapping(value = "/Access_Denied", method = RequestMethod.GET)
	public String accessDeniedPage(ModelMap model) {
		return "accessDenied";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage() {
		return "login";
	}

	@RequestMapping(value="/logout", method = RequestMethod.GET)
	public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null){    
			new SecurityContextLogoutHandler().logout(request, response, auth);
		}
		return "redirect:/login?logout";
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String viewUser(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
		String userId = request.getParameter("id");
		User user = null;
		if( userId != null ) {
			user = userService.findById( Long.parseLong(userId) );
		} else {
			user = new User();
			User defaultReportingTo = new User();
			defaultReportingTo.setId(0L);
			user.setReportingTo(defaultReportingTo);
		}
		
		List<User> users = userService.findAllActiveUsers();
		User noUser = new User();
		noUser.setSsoId("No Reporting");
		users.add(noUser);
		if( user.getId() != null ) {
			users.remove( user );
		}
		model.addAttribute("users", users);
		model.addAttribute("roles", userProfileService.findAll());
		model.addAttribute("user", user);
		return "user/user";
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String viewUsers(ModelMap model) {
		return "user/users";
	}

	/*
	 * This method will be called on form submission, handling POST request It
	 * also validates the user input
	 */
	@RequestMapping(value = "/user", method = RequestMethod.POST)
	public String saveRegistration(User user,
			BindingResult result, ModelMap model) {

		userService.save(user);
		
		log.debug("First Name : "+user.getFirstName());
		log.debug("Last Name : "+user.getLastName());
		log.debug("SSO ID : "+user.getSsoId());
		log.debug("Email : "+user.getEmail());
		log.debug("Checking UsrProfiles....");
		if(user.getUserProfiles()!=null){
			for(UserProfile profile : user.getUserProfiles()){
				log.debug("Profile : "+ profile.getType());
			}
		}
		model.addAttribute("success", "User " + user.getFirstName() + " has been registered successfully");
		return "user/registrationsuccess";
	}
	
	@RequestMapping(value = "/pr", method = RequestMethod.GET)
	public String viewPurchaseRequisition(ModelMap model){
		PurchaseRequisition purchaseRequisition = new PurchaseRequisition();
		PurchaseRequisitionItems purchaseRequisitionItems = new PurchaseRequisitionItems();
		model.addAttribute("purchaseRequisition", purchaseRequisition);
		model.addAttribute("purchaseRequisitionItems", purchaseRequisitionItems);
		return "pr/pr";
	}
	
	@RequestMapping(value = "/myPR", method = RequestMethod.GET)
	public String myPurchaseRequisitions(HttpServletRequest request, HttpServletResponse response, ModelMap model){
		Long userId = SpringContextUtil.getUserId();
		StringBuilder createdBy = new StringBuilder(userId.toString());
		List<Long> subordinates = userService.getAllSubordinateUserIds(userId);
		for (Long user : subordinates) {
			createdBy.append(',');
			createdBy.append(user.toString());
		}
		request.setAttribute("createdBy", createdBy);
		return "pr/mypr";
	}
	
	@RequestMapping(value = "/searchPR", method = RequestMethod.GET)
	public String searchPurchaseRequisitions(ModelMap model){
		return "pr/searchpr";
	}
}