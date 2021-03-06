package com.bridgelabz.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.dao.NoteDao;
import com.bridgelabz.dao.UserDao;
import com.bridgelabz.model.Label;
import com.bridgelabz.model.Note;
import com.bridgelabz.model.User;
import com.bridgelabz.utility.TokenGenerator;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteDao noteDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private TokenGenerator tokenGenerator;

	@Transactional
	public boolean create(Note note, String token, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		User user = userDao.getById(userId);
		note.setUserId(user);
		int id = noteDao.create(note);
		return (id > 0) ? true : false;
	}

	public List<Note> retrieve(String token, HttpServletRequest request) {
		List<Note> notes = null;
		int userId = tokenGenerator.verifyToken(token);
		User user = userDao.getById(userId);
		if (user != null)
			notes = noteDao.retrieve(user);
		return notes;
	}

	@Transactional
	public Note updateNote(int noteId, Note note, String token, HttpServletRequest request) {
		Note newNote = null;
		int userId = tokenGenerator.verifyToken(token);
		User user = userDao.getById(userId);
		if (user != null) {
			newNote = noteDao.getById(noteId);
			if (newNote != null && newNote.getUserId().getId() == userId) {
				newNote.setTitle(note.getTitle());
				newNote.setDescription(note.getDescription());
				newNote.setArchive(note.isArchive());
				newNote.setPinned(note.isPinned());
				newNote.setTrashed(note.isTrashed());
				noteDao.updateNote(newNote);
				return newNote;
			}
		}
		return null;
	}

	@Transactional
	public boolean deleteNote(int noteId, String token, HttpServletRequest request) {
		boolean result = false;
		int userId = tokenGenerator.verifyToken(token);
		User user = userDao.getById(userId);
		if (user != null) {
			result = noteDao.deleteNote(noteId);
		}
		return result;
	}

	@Transactional
	public boolean createLabel(Label label, String token, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		User user = userDao.getById(userId);
		label.setUserId(user);
		int id = noteDao.createLabel(label);
		return (id > 0) ? true : false;
	}

	@Override
	public List<Label> retrieveLabel(String token, HttpServletRequest request) {
		List<Label> labels = null;
		int userId = tokenGenerator.verifyToken(token);
		User user = userDao.getById(userId);
		if (user != null)
			labels = noteDao.retrieveLabel(user);
		return labels;
	}

	@Transactional
	public Label updateLabel(int labelId, Label label, String token, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		User user = userDao.getById(userId);
		if (user != null) {
			Label newLabel = noteDao.getByLabelId(labelId);
			if (newLabel != null) {
				newLabel.setLabelName(label.getLabelName());
				noteDao.updateLabel(newLabel);
				return newLabel;
			}
		}
		return null;
	}

	@Transactional
	public boolean deleteLabel(int labelId, String token, HttpServletRequest request) {
		boolean result = false;
		int userId = tokenGenerator.verifyToken(token);
		User user = userDao.getById(userId);
		if (user != null)
			result = noteDao.deleteLabel(labelId);
		return result;
	}

	public boolean addLabelToNote(int noteId, int labelId, HttpServletRequest request) {
		Note note = noteDao.getById(noteId);
		Label label = noteDao.getByLabelId(labelId);
		List<Label> labels = note.getListOfLabels();
		labels.add(label);
		if (!labels.isEmpty()) {
			note.setListOfLabels(labels);
			noteDao.updateNote(note);
			return true;
		}
		return false;
	}

	public boolean removeLabelFromNote(int noteId, int labelId, HttpServletRequest request) {
		Note note = noteDao.getById(noteId);
		List<Label> labels = note.getListOfLabels();
		if (!labels.isEmpty()) {
			labels = labels.stream().filter(label -> label.getLabelId() != labelId).collect(Collectors.toList());
			note.setListOfLabels(labels);
			noteDao.updateNote(note);
			return true;
		}
		return false;
	}

}
