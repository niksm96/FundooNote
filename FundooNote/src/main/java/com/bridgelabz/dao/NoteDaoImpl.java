package com.bridgelabz.dao;

import java.util.List;
import java.util.Optional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.bridgelabz.model.Label;
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
		List<Note> notes = criteria.list();
		return notes;
	}

	public void updateNote(Note note) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.update(note);
		transaction.commit();
		session.close();
	}

	public boolean deleteNote(int noteId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hqlQuery = "delete Label where noteId = :noteId";
		Query query = session.createQuery(hqlQuery);
		query.setInteger("noteId", noteId);
		int noOfRowsEffected = query.executeUpdate();
		tx.commit();
		session.close();
		return (noOfRowsEffected > 0)? true:false;
	}

	public Note getById(int noteId) {
		Session session = sessionFactory.openSession();
		String hqlQuery = "from Note where noteId = :noteId";
		Query query = session.createQuery(hqlQuery);
		query.setInteger("noteId", noteId);
		Note existingNote = (Note) query.uniqueResult();
		Optional<Note> isUserNull = Optional.ofNullable(existingNote);
		if (isUserNull.isPresent())
			session.close();
		return existingNote;
	}

	@Override
	public int createLabel(Label label) {
		Session session = sessionFactory.getCurrentSession();
		int labelId = (Integer) session.save(label);
		return labelId;
	}

	@Override
	public void updateLabel(Label label) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		session.update(label);
		transaction.commit();
		session.close();
	}

	@Override
	public boolean deleteLabel(int labelId) {
		Session session = sessionFactory.openSession();
		Transaction transaction = session.beginTransaction();
		String hqlQuery = "delete Label where labelId = :labelId";
		Query query = session.createQuery(hqlQuery);
		query.setInteger("labelId", labelId);
		int noOfRowsEffected = query.executeUpdate();
		transaction.commit();
		session.close();
		return (noOfRowsEffected > 0)? true:false;
	}
	
	@SuppressWarnings("unchecked")
	public List<Label> retrieveLabel(User user) {
		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Label.class).add(Restrictions.eq("userId", user));
		List<Label> labels = criteria.list();
		return labels;
	}

	@Override
	public Label getByLabelId(int labelId) {
		Session session = sessionFactory.openSession();
		String hqlQuery = "from Label where labelId = :labelId";
		Query query = session.createQuery(hqlQuery);
		query.setInteger("labelId", labelId);
		Label existingLabel = (Label) query.uniqueResult();
		Optional<Label> isUserNull = Optional.ofNullable(existingLabel);
		if (isUserNull.isPresent())
			session.close();
		return existingLabel;
	}
}
