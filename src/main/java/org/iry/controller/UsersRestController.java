/**
 * 
 */
package org.iry.controller;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.iry.dto.SearchCriteria;
import org.iry.dto.user.UserDto;
import org.iry.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author vaibhavp
 *
 */
@RestController
@RequestMapping("/rest/user")
public class UsersRestController {
	
	private static final Logger log = Logger.getLogger(UsersRestController.class);
	
	@Autowired
	UserService userService;
	
	@RequestMapping(value = "/_search", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserDto>> listAllUsers(@RequestBody SearchCriteria searchCriteria) {
		try {
			log.debug("Fetching all users...");
			List<UserDto> users = userService.findUsers(searchCriteria);
			return new ResponseEntity<List<UserDto>>(users, HttpStatus.OK);
		} catch( Exception e ) {
			log.error("Error in Fetching users...", e);
			return new ResponseEntity<List<UserDto>>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@RequestMapping(value = "/{id}/changepassword", method = RequestMethod.POST, produces = MediaType.TEXT_HTML_VALUE )
    public ResponseEntity<String> changePassword(@PathVariable("id") Long id, @RequestBody Map<String, String> data) {
		try {
			userService.changePassword(id, data.get("newPassword"));
			return new ResponseEntity<String>("Password changed successfully!!", HttpStatus.OK);
		} catch( Exception e ) {
			log.error("Error in changing password...", e);
			return new ResponseEntity<String>("Error changing password - " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@RequestMapping(value = "/{id}/resetpassword", method = RequestMethod.PUT, produces = MediaType.TEXT_HTML_VALUE )
    public ResponseEntity<String> resetPassword(@PathVariable("id") Long id) {
		try {
			userService.changePassword(id, null);
			return new ResponseEntity<String>("Password reset successfully!!", HttpStatus.OK);
		} catch( Exception e ) {
			log.error("Error in changing password...", e);
			return new ResponseEntity<String>("Error resetting password - " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
	
	@RequestMapping(value = "/{id}/changestatus", method = RequestMethod.PUT, produces = MediaType.TEXT_HTML_VALUE )
    public ResponseEntity<String> changeStatus(@PathVariable("id") Long id) {
		try {
			userService.changeStatus(id);
			return new ResponseEntity<String>("Status Changed successfully!!", HttpStatus.OK);
		} catch( Exception e ) {
			log.error("Error in changing password...", e);
			return new ResponseEntity<String>("Error changing status - " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

}
