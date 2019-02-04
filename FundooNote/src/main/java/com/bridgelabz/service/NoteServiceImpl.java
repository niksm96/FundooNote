package com.bridgelabz.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.dao.NoteDao;
import com.bridgelabz.model.Note;

@Service
public class NoteServiceImpl implements NoteService {

	@Autowired
	private NoteDao noteDao;

	@Transactional
	public boolean create(Note note, HttpServletRequest request) {
		int noteId = noteDao.create(note);
		if (noteId > 0)
			return true;
		return false;
	}

	public List<Note> retrieve(HttpServletRequest request) {
		List<Note> listOfNote = noteDao.retrieve();
		if(!listOfNote.isEmpty())
			return listOfNote;
		else
			return null;
	}

	@Transactional
	public Note updateNote(int noteId, Note note, HttpServletRequest request) {
		Note newNote = noteDao.getById(noteId);
		if (newNote != null) {
			newNote.setTitle(note.getTitle());
			newNote.setDescription(note.getDescription());
			newNote.setArchive(note.isArchive());
			newNote.setPinned(note.isPinned());
			newNote.setTrashed(note.isTrashed());
			noteDao.updateNote(noteId, newNote);
		}
		return newNote;
	}

	@Transactional
	public boolean deleteNote(int noteId, HttpServletRequest request) {
		boolean res = noteDao.deleteNote(noteId);
		if (res)
			return true;
		else
			return false;
	}

}
