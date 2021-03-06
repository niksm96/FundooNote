package com.bridgelabz.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.Label;
import com.bridgelabz.model.Note;
import com.bridgelabz.service.NoteService;

@RestController
public class NoteController {

	@Autowired
	private NoteService noteService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<Void> createNote(@RequestBody Note note,@RequestHeader(value = "token") String token, HttpServletRequest request) {
			try {
				if (noteService.create(note, token,request))
					return new ResponseEntity<Void>(HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<Void>(HttpStatus.CONFLICT);
			}
		return new ResponseEntity<Void>(HttpStatus.CONFLICT);
	}
	
	@RequestMapping(value = "/updatenote", method = RequestMethod.POST)
	public ResponseEntity<?> updateNote(@RequestParam("noteId") int noteId,@RequestBody Note note,@RequestHeader(value = "token") String token, HttpServletRequest request){
		Note updatedNote = noteService.updateNote(noteId,note,token, request);
		if (updatedNote != null) {
			return new ResponseEntity<String>("Updated Successfully",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Couldn't update", HttpStatus.CONFLICT);
	}
	
	@RequestMapping(value = "/deletenote", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@RequestParam("noteId") int noteId,@RequestHeader(value = "token") String token,HttpServletRequest request){
		boolean result = noteService.deleteNote(noteId,token, request);
		if (result) {
			return new ResponseEntity<String>("Deleted successfully",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Couldn't delete", HttpStatus.CONFLICT);
	}
	
	@RequestMapping(value = "/retrieve", method = RequestMethod.GET)
	public ResponseEntity<?> retrieve(@RequestHeader(value = "token") String token, HttpServletRequest request){
		List<Note> listOfNote = noteService.retrieve(token,request);
		if(!listOfNote.isEmpty())
			return new ResponseEntity<List<Note>>(listOfNote, HttpStatus.FOUND);
		else
			return new ResponseEntity<String>("No notes to fetch", HttpStatus.NOT_FOUND);
	}
	

	@RequestMapping(value = "/createlabel", method = RequestMethod.POST)
	public ResponseEntity<?> createLabel(@RequestBody Label label, @RequestHeader(value = "token")	String token,HttpServletRequest request) {
			try {
				if (noteService.createLabel(label, token,request))
					return new ResponseEntity<String>("Label created successfully",HttpStatus.OK);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<String>("Label creation unsuccessful",HttpStatus.CONFLICT);
			}
		return new ResponseEntity<String>("Label created unsuccessful",HttpStatus.CONFLICT);
	}
	
	@RequestMapping(value = "/updatelabel", method = RequestMethod.POST)
	public ResponseEntity<?> updateLabel(@RequestParam("labelId") int labelId,@RequestBody Label label, @RequestHeader(value = "token") String token,HttpServletRequest request){
		Label updatedLabel = noteService.updateLabel(labelId,label,token ,request);
		if (updatedLabel != null) {
			return new ResponseEntity<String>("Updated Successfully",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Couldn't update", HttpStatus.CONFLICT);
	}
	
	@RequestMapping(value = "/deletelabel", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteLabel(@RequestParam("labelId") int labelId,@RequestHeader(value = "token") String token,HttpServletRequest request){
		boolean result = noteService.deleteLabel(labelId,token, request);
		if (result) {
			return new ResponseEntity<String>("Deleted successfully",HttpStatus.OK);
		}
		return new ResponseEntity<String>("Couldn't delete", HttpStatus.CONFLICT);
	}
	
	@RequestMapping(value = "/retrievelabel", method = RequestMethod.GET)
	public ResponseEntity<?> retrieveLabel(@RequestHeader(value = "token")	String token,HttpServletRequest request){
		List<Label> listOfLabels = noteService.retrieveLabel(token,request);
		if(!listOfLabels.isEmpty())
			return new ResponseEntity<List<Label>>(listOfLabels, HttpStatus.FOUND);
		else
			return new ResponseEntity<String>("No labels to fetch", HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(value = "/addlabeltonote", method = RequestMethod.PUT)
	public ResponseEntity<?> addLabelToNote(@RequestParam("noteId") int noteId,@RequestParam("labelId") int labelId, HttpServletRequest request){
		boolean result = noteService.addLabelToNote(noteId, labelId, request);
		if(result) {
			return new ResponseEntity<String>("Label added to note successfully",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Label could'nt be added to note",HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(value = "/removelabelfromnote", method = RequestMethod.DELETE)
	public ResponseEntity<?> removeLabelFromNote(@RequestParam("noteId") int noteId,@RequestParam("labelId") int labelId, HttpServletRequest request){
		boolean result = noteService.removeLabelFromNote(noteId, labelId, request);
		if(result) {
			return new ResponseEntity<String>("Label removed successfully from note",HttpStatus.OK);
		}
		else {
			return new ResponseEntity<String>("Label could'nt be removed from note",HttpStatus.CONFLICT);
		}
	}
	
}
