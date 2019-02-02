package com.bridgelabz.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.dao.UserDao;
import com.bridgelabz.model.User;
import com.bridgelabz.utility.TokenGenerator;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private TokenGenerator tokenGenerator;

	@Transactional
	public boolean register(User user, HttpServletRequest request) {
		int id = userDao.register(user);
		if (id > 0) {
			String token = tokenGenerator.generateToken(String.valueOf(id));
			System.out.println(token);
			return true;
		}
		return false;
	}

	@Transactional
	public User login(String emailId, String password, HttpServletRequest request) {
		User exisitingUser = userDao.login(emailId, password);
		if (exisitingUser != null) {
			return exisitingUser;
		}
		return null;
	}
	
	@Transactional
	public User update(int id, User user, HttpServletRequest request) {
		User newUser = userDao.getById(id);
		if (newUser != null) {
			newUser.setEmailId(user.getEmailId());
			newUser.setName(user.getName());
			newUser.setPassword(user.getPassword());
			newUser.setMobileNumber(user.getMobileNumber());
			userDao.updateUser(id, newUser);
		}
		return newUser;
	}

	@Transactional
	public boolean delete(int id, HttpServletRequest request) {
		boolean res = userDao.deleteUser(id);
		if (res) {
			return true;
		} else
			return false;
	}

}
