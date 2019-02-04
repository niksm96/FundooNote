package com.bridgelabz.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.bridgelabz.model.Note;

public interface NoteService {
	boolean create(Note note, HttpServletRequest request);
	
	List<Note> retrieve(HttpServletRequest request);

	Note updateNote(int noteId, Note note, HttpServletRequest request);

	boolean deleteNote(int noteId, HttpServletRequest request);
}