package org.iry.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.iry.model.pr.PurchaseRequisition;
import org.iry.model.pr.PurchaseRequisitionItems;
import org.iry.model.user.User;
import org.iry.model.user.UserProfile;
import org.iry.service.user.UserProfileService;
import org.iry.service.user.UserService;
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
	public String homePage(ModelMap model) {
		return "home";
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
	
	@RequestMapping(value = "/newUser", method = RequestMethod.GET)
	public String newRegistration(ModelMap model) {
		User user = new User();
		model.addAttribute("user", user);
		model.addAttribute("roles", userProfileService.findAll());
		model.addAttribute("users", userService.findAllActiveUsers());
		return "user/newuser";
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String viewUsers(ModelMap model) {
		return "user/users";
	}

	/*
	 * This method will be called on form submission, handling POST request It
	 * also validates the user input
	 */
	@RequestMapping(value = "/newUser", method = RequestMethod.POST)
	public String saveRegistration(User user,
			BindingResult result, ModelMap model) {

		userService.save(user);
		
		log.debug("First Name : "+user.getFirstName());
		log.debug("Last Name : "+user.getLastName());
		log.debug("SSO ID : "+user.getSsoId());
		log.debug("Password : "+user.getPassword());
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
	
	@RequestMapping(value = "/newPR", method = RequestMethod.GET)
	public String newPurchaseRequisition(ModelMap model){
		PurchaseRequisition purchaseRequisition = new PurchaseRequisition();
		PurchaseRequisitionItems purchaseRequisitionItems = new PurchaseRequisitionItems();
		model.addAttribute("purchaseRequisition", purchaseRequisition);
		model.addAttribute("purchaseRequisitionItems", purchaseRequisitionItems);
		return "pr/newpr";
	}
	
	@RequestMapping(value = "/myPR", method = RequestMethod.GET)
	public String myPurchaseRequisitions(ModelMap model){
		return "pr/mypr";
	}
	
	@RequestMapping(value = "/editPR", method = RequestMethod.GET)
	public String editPurchaseOrder(ModelMap model){
		PurchaseRequisition purchaseRequisition = new PurchaseRequisition();
		PurchaseRequisitionItems purchaseRequisitionItems = new PurchaseRequisitionItems();
		model.addAttribute("purchaseRequisition", purchaseRequisition);
		model.addAttribute("purchaseRequisitionItems", purchaseRequisitionItems);
		return "pr/newpr";
	}
}