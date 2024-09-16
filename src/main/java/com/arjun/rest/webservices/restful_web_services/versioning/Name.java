package com.arjun.rest.webservices.restful_web_services.versioning;

public class Name {

	private String firstName;
	private String secondName;
	public Name(String firstName, String secondName) {
		super();
		this.firstName = firstName;
		this.secondName = secondName;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getSecondName() {
		return secondName;
	}
	@Override
	public String toString() {
		return "Name [firstName=" + firstName + ", SecondName=" + secondName + "]";
	}
	
	
}
