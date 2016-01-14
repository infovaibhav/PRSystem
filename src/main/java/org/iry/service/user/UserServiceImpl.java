package org.iry.service.user;

import java.util.List;

import org.iry.dao.user.UserDao;
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
	public List<User> findAllActiveUsers() {
		return dao.findAllActiveUsers();
	}

	@Override
	public List<User> findAllUsers() {
		return dao.findAllUsers();
	}
	
}
