package com.bridgelabz.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.model.User;

@Repository
public class UserDaoImpl implements UserDao {
	@Autowired
	private SessionFactory sessionFactory;

	public int register(User user) {
		int userId = 0;
		Session session = sessionFactory.getCurrentSession();
		userId = (Integer) session.save(user);
		return userId;
	}

	public User login(String emailId, String password) {
		Session session = sessionFactory.openSession();
		String hqlQuery = "from User where emailId = :emailId and password = :password";
		Query query = session.createQuery(hqlQuery);
		query.setString("emailId", emailId);
		query.setString("password", password);
		User existingUser = (User) query.uniqueResult();
		session.close();
		return existingUser;
	}

	public void updateUser(int id, User user) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(user);
		tx.commit();
		session.close();
	}

	public List<User> getUsersList() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean deleteUser(int id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hqlQuery = "delete User where id = :id";
		Query query = session.createQuery(hqlQuery);
		query.setInteger("id", id);
		int i = query.executeUpdate();
		tx.commit();
		session.close();
		if (i > 0)
			return true;
		return false;
	}

	public User getById(int id) {
		Session session = sessionFactory.openSession();
		String hqlQuery = "from User where id = :id";
		Query query = session.createQuery(hqlQuery);
		query.setInteger("id", id);
		User existingUser = (User) query.uniqueResult();
		if (existingUser != null) {
			session.close();
			return existingUser;
		} else {
			return null;
		}
	}
}
