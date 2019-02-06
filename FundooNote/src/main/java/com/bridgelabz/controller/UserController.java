package com.bridgelabz.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.model.User;
import com.bridgelabz.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<Void> registerUser(@RequestBody User user, HttpServletRequest request) {
		try {
			if (userService.register(user, request))
				return new ResponseEntity<Void>(HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
		return new ResponseEntity<Void>(HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ResponseEntity<?> loginUser(@RequestParam String emailId, @RequestParam String password,
			HttpServletRequest request,HttpServletResponse response) {
		User existingUser = userService.login(emailId, password, request,response);
		try {
			if (existingUser != null) {
				return new ResponseEntity<User>(existingUser, HttpStatus.FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<String>(
					"Either you are not an authorized user or your email-id or password is incorrect",
					HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<String>(
				"Either you are not an authorized user or your email-id or password is incorrect",
				HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> updateUser(@RequestParam("id") int id, @RequestBody User user,
			HttpServletRequest request) {
		User updatedUser = userService.update(id, user, request);
		if (updatedUser != null) {
			return new ResponseEntity<String>("Updated Successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Couldn't update", HttpStatus.CONFLICT);
	}

	@RequestMapping(value = "/delete", method = RequestMethod.DELETE)
	public ResponseEntity<?> delete(@RequestParam int id, HttpServletRequest request) {
		boolean result = userService.delete(id, request);
		if (result) {
			return new ResponseEntity<String>("Deleted successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Couldn't delete", HttpStatus.CONFLICT);
	}

	@RequestMapping("/activationstatus/{token:.+}")
	public ResponseEntity<?> activateUser(@PathVariable("token") String token,HttpServletRequest request) {
		User updatedUser = userService.activationStatus(token, request);
		if (updatedUser != null) {
			return new ResponseEntity<String>("Activated Successfully", HttpStatus.OK);
		}
		return new ResponseEntity<String>("Couldn't activate", HttpStatus.CONFLICT);
	}

}
