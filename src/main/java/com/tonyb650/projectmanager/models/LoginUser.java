package com.tonyb650.projectmanager.models;

import jakarta.validation.constraints.NotEmpty;

public class LoginUser {
	@NotEmpty(message="Email is required") // This is probably redundant and unnecessary
	private String email;
	
	@NotEmpty(message="Password is required") // This is probably redundant and unnecessary
	private String password;
	
	public LoginUser() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
