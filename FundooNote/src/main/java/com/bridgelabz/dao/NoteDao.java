package com.bridgelabz.dao;

import java.util.List;

import com.bridgelabz.model.Note;
import com.bridgelabz.model.User;

public interface NoteDao {
	int create(Note note);
	
	List<Note> retrieve(User user);
	
	void updateNote(int noteId, Note note);
	
	boolean deleteNote(int noteId);
	
	Note getById(int noteId);
}
