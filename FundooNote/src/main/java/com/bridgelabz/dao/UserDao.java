package com.bridgelabz.dao;

import java.util.List;

import com.bridgelabz.model.User;

public interface UserDao {
	int register(User user);

	User login(String emailId);

	void updateUser(int id, User user);

	List<User> getUsersList();

	boolean deleteUser(int id);

	User getById(int id);
}