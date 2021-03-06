package com.bridgelabz.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.model.Label;
import com.bridgelabz.model.Note;

public interface NoteService {
	boolean create(Note note,String token, HttpServletRequest request);
	
	List<Note> retrieve(String token, HttpServletRequest request);

	Note updateNote(int noteId,Note note,String token, HttpServletRequest request);

	boolean deleteNote(int noteId, String token,HttpServletRequest request);
	
	boolean createLabel(Label label,String token,HttpServletRequest request);
	
	List<Label> retrieveLabel(String token,HttpServletRequest request);
	
	Label updateLabel(int labelId, Label label,String token, HttpServletRequest request);
	
	boolean deleteLabel(int labelId,String token ,HttpServletRequest request);

	boolean addLabelToNote(int noteId, int labelId, HttpServletRequest request);
	
	boolean removeLabelFromNote(int noteId,int labelId,HttpServletRequest request);
	
}
