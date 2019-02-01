package com.bridgelabz.dao;

import java.util.List;

import com.bridgelabz.model.User;

public interface UserDao {
	int register(User user);

	User login(String emailId,String password);

	void updateUser(String emailId, User user);

	List<User> getUsersList();

	boolean deleteUser(String emailId);
	
	User getByEmailId(String emailId);
}