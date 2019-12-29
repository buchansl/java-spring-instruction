package com.prs.business;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class User {
	@Id

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String userName;

	private String password;
	private String firstName;
	private String lastName;

	private String phoneNumber;
	private String email;

	private boolean isReviewer;

	private boolean isAdmin;

	public User() {
		super();
	}

	public User(int id, String userName, String password, String fName, String lName, String phoneNumber, String email,
			boolean isReviewer, boolean isAdmin) {
		super();
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.firstName = fName;
		this.lastName = lName;
		this.phoneNumber = phoneNumber;
		this.email = email;
		this.isReviewer = isReviewer;
		this.isAdmin = isAdmin;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getfirstName() {
		return firstName;
	}

	public void setfirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getlastName() {
		return lastName;
	}

	public void setlastName(String lastName) {
		this.lastName = lastName;
	}

	public String getphoneNumber() {
		return phoneNumber;
	}

	public void setphoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getIsReviewer() {
		return isReviewer;
	}

	public void setIsReviewer(boolean isReviewer) {
		this.isReviewer = isReviewer;
	}

	public boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", userName=" + userName + ", password=" + password + ", fName=" + firstName
				+ ", lName=" + lastName + ", pNumber=" + phoneNumber + ", email=" + email + ", isReviewer=" + isReviewer
				+ ", isAdmin=" + isAdmin + "]";
	}

}