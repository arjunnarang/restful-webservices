package com.arjun.rest.webservices.restful_web_services.exception;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.arjun.rest.webservices.restful_web_services.user.PostNotFoundException;
import com.arjun.rest.webservices.restful_web_services.user.UserNotFoundException;


//Whenever an exception occurs, spring throws its own exceptions which are handled in 'ResponseEntityExceptionHandler' class
//so now here we are creating a customised exception handler which inherits the functions of 'ResponseEntityExceptionHandler'
@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	

	@ExceptionHandler(Exception.class)  //this handles all kind of exceptions
	// ResponseEntity is the class that handles all exceptions defined in springboot under class ResponseEntityExceptionHandler
	
	public final ResponseEntity<ErrorDetails> handleAllExceptions(Exception ex, WebRequest request) throws Exception {
		
		//We created an object of ErrorDetails using constructor
		// timestamp => LocalDate.now()
		// message => ex.getMessage()
		// details => request.getDescription(false)
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails> (errorDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)  //this handles User not found exception
    public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception {
		
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		
		return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);
	}
	
	
	@Override  //as this method in ResponseEntityClass is not final, we can override it
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

		
		
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getFieldError().getDefaultMessage(), request.getDescription(false));
		
		//ex.getFieldError().getDefaultMessage() - return only the first error
		
		return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
	}
	
	
	@ExceptionHandler(PostNotFoundException.class)  //this handles Post not found exception
	public final ResponseEntity<ErrorDetails> handlePostNotFoundException(Exception ex, WebRequest request) throws Exception{
		
		ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		
		
		return new ResponseEntity<ErrorDetails> (errorDetails, HttpStatus.NOT_FOUND);
		
	}
}
