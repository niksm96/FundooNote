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
		if (id > 0) {
			return true;
		}
		return false;
	}

	public List<Note> retrieve(String token, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		User user = userDao.getById(userId);
		if (user != null) {
			List<Note> listOfNote = noteDao.retrieve(user);
			if (!listOfNote.isEmpty())
				return listOfNote;
		}
		return null;
	}

	@Transactional
	public Note updateNote(int noteId, Note note, String token, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		User user = userDao.getById(userId);
		if (user != null) {
			Note newNote = noteDao.getById(noteId);
			if (newNote != null) {
				newNote.setTitle(note.getTitle());
				newNote.setDescription(note.getDescription());
				newNote.setArchive(note.isArchive());
				newNote.setPinned(note.isPinned());
				newNote.setTrashed(note.isTrashed());
				noteDao.updateNote(noteId, newNote);
				return newNote;
			}
		}
		return null;
	}

	@Transactional
	public boolean deleteNote(int noteId, String token, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		User user = userDao.getById(userId);
		if (user != null) {
			boolean res = noteDao.deleteNote(noteId);
			if (res)
				return true;
		}
		return false;
	}

	@Transactional
	public boolean createLabel(Label label, String token, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		User user = userDao.getById(userId);
		label.setUserId(user);
		int id = noteDao.createLabel(label);
		if (id > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<Label> retrieveLabel(String token, HttpServletRequest request) {
		int userId = tokenGenerator.verifyToken(token);
		User user = userDao.getById(userId);
		if (user != null) {
			List<Label> listOfLabels = noteDao.retrieveLabel(user);
			if (!listOfLabels.isEmpty())
				return listOfLabels;
		}
		return null;
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
		int userId = tokenGenerator.verifyToken(token);
		User user = userDao.getById(userId);
		if (user != null) {
			boolean res = noteDao.deleteLabel(labelId);
			if (res)
				return true;
		}
		return false;
	}

	public boolean addLabelToNote(int noteId, int labelId, HttpServletRequest request) {
		Note note = noteDao.getById(noteId);
		Label label = noteDao.getByLabelId(labelId);
		List<Label> listOfLabels = note.getListOfLabels();
		listOfLabels.add(label);
		if (!listOfLabels.isEmpty()) {
			note.setListOfLabels(listOfLabels);
			noteDao.updateNote(noteId, note);
			return true;
		}
		return false;
	}

	public boolean removeLabelFromNote(int noteId, int labelId, HttpServletRequest request) {
		Note note = noteDao.getById(noteId);
		List<Label> listOfLabels = note.getListOfLabels();
		if (!listOfLabels.isEmpty()) {
			listOfLabels = listOfLabels.stream().filter(label -> label.getLabelId() != labelId)
					.collect(Collectors.toList());
			note.setListOfLabels(listOfLabels);
			noteDao.updateNote(noteId, note);
			return true;
		}
		return false;
	}

}
