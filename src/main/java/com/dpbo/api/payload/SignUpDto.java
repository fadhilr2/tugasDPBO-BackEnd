package com.dpbo.api.payload;

public class SignUpDto {
	private String fullName;
	private String phoneNumber;
	private String password;
	
	public SignUpDto(String fullName, String phoneNumber, String password) {
		this.fullName = fullName;
		this.phoneNumber = phoneNumber;
		this.password = password;
	}

	public String getFullName() {
		return fullName;
	}


	public String getPhoneNumber() {
		return phoneNumber;
	}


	public String getPassword() {
		return password;
	}

	
	
}
