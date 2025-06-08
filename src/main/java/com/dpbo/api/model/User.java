package com.dpbo.api.model;

import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
public class User {
	@Id
	private String id;
	private String fullName;
	private String phoneNumber;
	private String password;
	private Set<Role> roles;
	
	public User() {
		
	}
	
	public User(String id, String fullName, String phoneNumber, String password, Set<Role> roles) {
		this.id = id;
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.password = password;
		this.roles = roles;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Set<Role> getRoles() {
		return roles;
	}
	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", fullName=" + fullName + ", phoneNumber=" + phoneNumber + ", password=" + password
				+ ", roles=" + roles + "]";
	}
	
	
	
	
}
