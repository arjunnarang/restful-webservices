package com.arjun.rest.webservices.restful_web_services.exception;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ErrorDetails extends RuntimeException {

	//We want to show :-
	//1) Timestamp pf when the error occured
	//2) Message about the error
	//3) Some extra details
	
	private LocalDateTime timestamp;
	private String message;
	private String details;
	
	public ErrorDetails(LocalDateTime timestamp, String message, String details) {
		super();
		this.timestamp = timestamp;
		this.message = message;
		this.details = details;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public String getMessage() {
		return message;
	}

	public String getDetails() {
		return details;
	}
	
	
	
}
