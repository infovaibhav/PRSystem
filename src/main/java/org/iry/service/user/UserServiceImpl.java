package org.iry.service.user;

import java.util.ArrayList;
import java.util.List;

import org.iry.dao.user.UserDao;
import org.iry.dto.SearchCriteria;
import org.iry.dto.user.UserDto;
import org.iry.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{

	@Autowired
	private UserDao dao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	
	public void save(User user){
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		dao.save(user);
	}
	
	public User findById(long id) {
		return dao.findById(id);
	}

	public User findBySso(String sso) {
		return dao.findBySSO(sso);
	}
	
	@Override
	public List<UserDto> findAllActiveUsers() {
		return dao.findAllActiveUsers();
	}

	@Override
	public List<UserDto> findUsers(SearchCriteria searchCriteria) {
		return convertToDto(dao.findUsers(searchCriteria));
	}

	private List<UserDto> convertToDto(List<User> users) {
		List<UserDto> userDtos = new ArrayList<UserDto>();
		if( users != null ) {
			for (User user : users) {
				UserDto userDto = new UserDto();
				userDto.setId(user.getId());
				userDto.setFirstName(user.getFirstName());
				userDto.setLastName(user.getLastName());
				userDto.setSsoId(user.getSsoId());
				userDto.setEmail(user.getEmail());
				userDto.setAuthorizedTransactionLimit(user.getAuthorizedTransactionLimit());
				userDto.setReportingTo(user.getReportingToStr());
				userDto.setRoles(user.getUserProfileStr());
				userDto.setStatus(user.getIsActive() ? "Active" : "Inactive");
				userDtos.add(userDto);
			}
		}
		return userDtos;
	}
	
}
