package com.bridgelabz.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.dao.UserDao;
import com.bridgelabz.model.User;

@Transactional
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	public boolean register(User user, HttpServletRequest request) {
		int id = userDao.register(user);
		if (id > 0) {
			return true;
		}
		return false;
	}

	public User login(String emailId, String password, HttpServletRequest request) {
		User exisitingUser = userDao.login(emailId, password);
		if (exisitingUser != null) {
			return exisitingUser;
		}
		return null;
	}

	public User update(String emailId, User user, HttpServletRequest request) {
		User newUser = userDao.getByEmailId(emailId);
		if (newUser != null) {
			newUser.setName(user.getName());
			newUser.setPassword(user.getPassword());
			newUser.setMobileNumber(user.getMobileNumber());
			userDao.updateUser(emailId, newUser);
			return newUser;

		}
		return null;
	}

	public User getByEmailId(String emailId, HttpServletRequest request) {
		User exisitingUser = userDao.getByEmailId(emailId);
		if (exisitingUser != null) {
			return exisitingUser;
		}
		return null;
	}

	public boolean delete(String emailId, HttpServletRequest request) {
		boolean res = userDao.deleteUser(emailId);
		if (res) {
			return true;
		} else
			return false;
	}

}
