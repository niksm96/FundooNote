package com.bridgelabz.dao;

import java.util.List;

import com.bridgelabz.model.Label;
import com.bridgelabz.model.Note;
import com.bridgelabz.model.User;

public interface NoteDao {
	int create(Note note);
	
	List<Note> retrieve(User user);
	
	void updateNote(Note note);
	
	boolean deleteNote(int noteId);
	
	Note getById(int noteId);
	
	int createLabel(Label label);
	
	void updateLabel(Label label);
	
	boolean deleteLabel(int labelId);
	
	List<Label> retrieveLabel(User user); 
	
	Label getByLabelId(int labelId);
}
