package com.bridgelabz.service;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.model.User;

public interface UserService {
	boolean register(User user, HttpServletRequest request);
	
	User login(String emailId, String password, HttpServletRequest request);
	
	User update(String emailId,User user, HttpServletRequest request);
	
	User getByEmailId(String emailId, HttpServletRequest request);
	
	boolean delete(String emailId,HttpServletRequest request);
}
