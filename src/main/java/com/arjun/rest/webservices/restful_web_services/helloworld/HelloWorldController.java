package com.arjun.rest.webservices.restful_web_services.helloworld;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	//Spring defined class to handle internationalization of the project
	private MessageSource messageSource;
	
	//Constructor injection
	public HelloWorldController(MessageSource messageSource) {
		this.messageSource = messageSource;
	}
	
	@GetMapping(path = "/hello-world")
	public String helloWorld() {
		return "Hello World";
	}

	
	//This will return hellow world bean that means a java object
	@GetMapping(path = "/hello-world-bean")
	public HelloWorldBean helloWorldBean() {
		return new HelloWorldBean("Hello World");
	}
	
	//defining and capturing path variable
	@GetMapping(path = "/hello-world/path-variable/{name}")
	public HelloWorldBean helloWorldPathVariable(@PathVariable String name) {
		return new HelloWorldBean("Hello World");
	}
	
	@GetMapping(path = "/hello-world-internationalized")
	public String helloWorldInternationalized() {
		
		//If a user sends a Accept header then LocaleContextHolder will return that locale otherwise local locale is sent
		Locale locale = LocaleContextHolder.getLocale();
		
		return messageSource.getMessage("good.morning.message", null, "Default Message", locale);
		//return "Hello World V2";
	}

}
