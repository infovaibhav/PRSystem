/**
 * 
 */
package org.iry.configuration;

import org.iry.model.user.User;
import org.iry.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 * @author vaibhavp
 *
 */
@Component
public class ReportingToUserConverter implements Converter<Object, User>{

	@Autowired
	UserService userService;

	public User convert(Object element) {
		if( element instanceof User) {
			return (User)element;
		}
		Long id = Long.parseLong((String)element);
		User reportingTo= userService.findById(id);
		return reportingTo;
	}

}
