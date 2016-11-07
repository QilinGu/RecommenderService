package com.fayaz.recmain.recommender.rest.pojo;

public class Signup {
	
	private String username;
	private String password;
	private String email;
	private String customerName;
	private String customerSecret;
	
	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getCustomerSecret() {
		return customerSecret;
	}


	public void setCustomerSecret(String customerSecret) {
		this.customerSecret = customerSecret;
	}


	public Signup(){}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

}
