package com.bridgelabz.service;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.model.User;

public interface UserService {
	boolean register(User user, HttpServletRequest request);
	
	User login(String emailId, String password, HttpServletRequest request);
	
	User update(int id,User user, HttpServletRequest request);
	
	boolean delete(int id,HttpServletRequest request);
}
