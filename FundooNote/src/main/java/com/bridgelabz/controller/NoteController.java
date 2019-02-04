package com.bridgelabz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.Note;
import com.bridgelabz.service.NoteService;

@RestController
public class NoteController {

	@Autowired
	private NoteService noteService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<String> create(@RequestBody Note note, HttpServletRequest request) {
		try {
			if (noteService.create(note, request))
			return new ResponseEntity<String>("Note created successfully", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("Note creation unsuccessful", HttpStatus.CONFLICT);
	}
	
	@RequestMapping(value = "/updatenote", method = RequestMethod.POST)
	public ResponseEntity<?> updateUser(@RequestParam("noteId") int noteId,@RequestBody Note note, HttpServletRequest request){
		Note updatedNote = noteService.updateNote(noteId,note, request);
		if (updatedNote != null) {
			return new ResponseEntity<String>("Updated Successfully",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Couldn't update", HttpStatus.CONFLICT);
	}
	
	@RequestMapping(value = "/deletenote", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@RequestParam("noteId") int noteId,HttpServletRequest request){
		boolean result = noteService.deleteNote(noteId, request);
		if (result) {
			return new ResponseEntity<String>("Deleted successfully",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Couldn't delete", HttpStatus.CONFLICT);
	}
	
	@RequestMapping(value = "/retrieve", method = RequestMethod.GET)
	public ResponseEntity<?> retrieve(HttpServletRequest request){
		List<Note> listOfNote = noteService.retrieve(request);
		if(!listOfNote.isEmpty())
			return new ResponseEntity<List<Note>>(listOfNote, HttpStatus.FOUND);
		else
			return new ResponseEntity<String>("No notes to fetch", HttpStatus.NOT_FOUND);
	}
	
}
