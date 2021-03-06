package com.bridgelabz.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.dao.UserDao;
import com.bridgelabz.model.User;
import com.bridgelabz.utility.EmailSender;
import com.bridgelabz.utility.TokenGenerator;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private TokenGenerator tokenGenerator;

	@Autowired
	private EmailSender emailSender;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Transactional
	public boolean register(User user, HttpServletRequest request) {
		String token;
		user.setPassword(bcryptEncoder.encode(user.getPassword()));
		int id = userDao.register(user);
		if (id > 0) {
			token = tokenGenerator.generateToken(String.valueOf(id));
			StringBuffer requestUrl = request.getRequestURL();
			String activationUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
			activationUrl = activationUrl + "/activationstatus/" + token;
			emailSender.sendEmail("", "", activationUrl);
			return true;
		}
		return false;
	}

	@Transactional
	public User login(User user, HttpServletRequest request, HttpServletResponse response) {
		User exisitingUser = userDao.login(user.getEmailId());
		String token = null;
		boolean isMatch;
		if (exisitingUser != null) {
			isMatch = bcryptEncoder.matches(user.getPassword(), exisitingUser.getPassword());
			if (isMatch && exisitingUser.isActivationStatus()) {
				token = tokenGenerator.generateToken(String.valueOf(exisitingUser.getId()));
				response.setHeader("token", token);
				return exisitingUser;
			}
		}
		return null;
	}

	@Transactional
	public User update(String token, User user, HttpServletRequest request) {
		int id = tokenGenerator.verifyToken(token);
		User newUser = userDao.getById(id);
		if (newUser != null) {
			newUser.setEmailId(user.getEmailId());
			newUser.setName(user.getName());
			newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			newUser.setMobileNumber(user.getMobileNumber());
			userDao.updateUser(newUser);
		}
		return newUser;
	}

	@Transactional
	public boolean delete(String token, HttpServletRequest request) {
		int id = tokenGenerator.verifyToken(token);
		return userDao.deleteUser(id);
	}

	public User activationStatus(String token, HttpServletRequest request) {
		int id = tokenGenerator.verifyToken(token);
		System.out.println(id);
		User user = userDao.getById(id);
		if (user != null) {
			user.setActivationStatus(true);
			userDao.updateUser(user);
		}
		return user;
	}

	public boolean forgotPassword(String emailId, HttpServletRequest request) {
		User existingUser = userDao.login(emailId);
		if (existingUser != null) {
			String token = tokenGenerator.generateToken(String.valueOf(existingUser.getId()));
			StringBuffer requestUrl = request.getRequestURL();
			System.out.println(requestUrl);
			String forgotPasswordUrl = requestUrl.substring(0, requestUrl.lastIndexOf("/"));
			forgotPasswordUrl = forgotPasswordUrl + "/resetpassword/" + token;
			System.out.println(forgotPasswordUrl);
			emailSender.sendEmail("", "", forgotPasswordUrl);
			return true;
		}
		return false;
	}

	public User resetPassword(User user, String token, HttpServletRequest request) {
		int id = tokenGenerator.verifyToken(token);
		User existingUser = userDao.getById(id);
		if (existingUser != null) {
			existingUser.setPassword(bcryptEncoder.encode(user.getPassword()));
			userDao.updateUser(existingUser);
			return existingUser;
		}
		return null;
	}
}
