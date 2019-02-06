package com.bridgelabz.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.model.Note;
import com.bridgelabz.model.User;

@Repository
public class NoteDaoImpl implements NoteDao {

	@Autowired
	private SessionFactory sessionFactory;

	public int create(Note note) {
		int noteId = 0;
		Session session = sessionFactory.getCurrentSession();
		noteId = (Integer) session.save(note);
		return noteId;
	}

	@SuppressWarnings("unchecked")
	public List<Note> retrieve(User user) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Note.class).add(Restrictions.eq("userId", user));
		List<Note> listOfNotes = criteria.list();
		return listOfNotes;
	}

	public void updateNote(int noteId, Note note) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		session.update(note);
		tx.commit();
		session.close();
	}

	public boolean deleteNote(int noteId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hqlQuery = "delete Note where noteId = :noteId";
		Query query = session.createQuery(hqlQuery);
		query.setInteger("noteId", noteId);
		int i = query.executeUpdate();
		tx.commit();
		session.close();
		if (i > 0)
			return true;
		return false;
	}

	public Note getById(int noteId) {
		Session session = sessionFactory.openSession();
		String hqlQuery = "from Note where noteId = :noteId";
		Query query = session.createQuery(hqlQuery);
		query.setInteger("noteId", noteId);
		Note existingNote = (Note) query.uniqueResult();
		if (existingNote != null) {
			session.close();
			return existingNote;
		} else
			return null;
	}

}
