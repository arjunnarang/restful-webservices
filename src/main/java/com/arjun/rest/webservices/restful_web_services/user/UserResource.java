//Controller layer

package com.arjun.rest.webservices.restful_web_services.user;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
		User user = service.findOne(id);
		
		if(user == null) {
			throw new UserNotFoundException("id:" + id);
		}
		
		return user;
	}
	
	
	//Creating new user, 
	@PostMapping("/users")
	public ResponseEntity<User> createuser(@RequestBody User user) {
		User savedUser = service.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()  //this will return the current request that is /users
												  .path("/{id}")         //this will append the id of the newly created user on to current requests
												  .buildAndExpand(savedUser.getId())  //this will get the value of the created or savedUser and place in the /users/{id}
												  .toUri();  //this will compile the location url as localhost:8080/users/4
		
		//return http response status 201 which stands for created and location created above to fetch the currently created user as a response
		return ResponseEntity.created(location).build();
	}
}
