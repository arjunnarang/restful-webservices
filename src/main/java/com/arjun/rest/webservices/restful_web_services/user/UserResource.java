//Controller layer

package com.arjun.rest.webservices.restful_web_services.user;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserResource {

	//Using constructor injection as UserDaoService is handled by spring as all the business logic is written there
	private UserDaoService service;
	
	public UserResource(UserDaoService service) {
		this.service = service;
	}
	
	@GetMapping("/users")
	public List<User> retrieveAll(){
		return service.findAll();
	}
	
	@GetMapping(path = "/users/{id}")
	public User retrieveById(@PathVariable Integer id){
		return service.findOne(id);
	}
	
	
	//Creating new user, 
	@PostMapping("/users")
	public void createuser(@RequestBody User user) {
		service.save(user);
	}
}
