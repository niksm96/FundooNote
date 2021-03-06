package com.bridgelabz.dao;

import com.bridgelabz.model.User;

public interface UserDao {
	int register(User user);

	User login(String emailId);

	void updateUser(User user);

	boolean deleteUser(int id);

	User getById(int id);
}