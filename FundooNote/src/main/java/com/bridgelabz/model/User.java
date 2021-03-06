package com.bridgelabz.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "User")
public class User {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "emailId", unique = true)
	private String emailId;

	@Column(name = "password", length = 60)
	private String password;

	@Column(name = "mobileNumber")
	private long mobileNumber;
	
	@Column(name="activation_status")
	private boolean activationStatus;
	
	@OneToMany(mappedBy = "userId")
	@JsonIgnore
	private List<Note> listOfNotes;
	
	@OneToMany(mappedBy = "userId")
	@JsonIgnore
	private List<Label> listOfLabels;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(long mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	
	public boolean isActivationStatus() {
		return activationStatus;
	}

	public void setActivationStatus(boolean activationStatus) {
		this.activationStatus = activationStatus;
	}

	public List<Note> getListOfNotes() {
		return listOfNotes;
	}

	public void setListOfNotes(List<Note> listOfNotes) {
		this.listOfNotes = listOfNotes;
	}
	
	public List<Label> getListOfLabels() {
		return listOfLabels;
	}

	public void setListOfLabels(List<Label> listOfLabels) {
		this.listOfLabels = listOfLabels;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", emailId=" + emailId + ", password=" + password
				+ ", mobileNumber=" + mobileNumber + ", activationStatus=" + activationStatus + ", listOfNotes="
				+ listOfNotes + ", listOfLabels=" + listOfLabels + "]";
	}
	
}
