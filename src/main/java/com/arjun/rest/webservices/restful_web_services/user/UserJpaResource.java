//Controller layer

package com.arjun.rest.webservices.restful_web_services.user;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.arjun.rest.webservices.restful_web_services.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {

	//Using constructor injection as UserDaoService is handled by spring as all the business logic is written there	
	private UserRepository repository;
	
	public UserJpaResource(UserRepository repository) {
		this.repository = repository;
	}
	
	@GetMapping("/jpa/users")
	public List<User> retrieveAll(){
		
		//findAll is a method in JpaRepository which is being extended in UserRepository 
		return repository.findAll();
	}
	
	@GetMapping(path = "/jpa/users/{id}")
	//Adding entitymodel here so as to implement hateoas means adding links to other URLs in JSON responses 
	// When this url is hit http://localhost:8080/users/1
	//{
//	"id": 1,
//	"name": "Adam",
//	"birthdate": "1994-09-21",
//	"_links":{
//	"all-users":{
//	"href": "http://localhost:8080/users"
//	}
//	}
//	}
	
//	We get the above response returned. As we can see the we can directly go to all users link by clicking on href link
//	mentioned in above response
	public EntityModel<User> retrieveById(@PathVariable Integer id){
		Optional<User> user = repository.findById(id);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("id:" + id);
		}
		
		EntityModel<User> entityModel = EntityModel.of(user.get());
		
		//this creates a route or link to retrieve all users using WebMvcLinkBuilder
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrieveAll());
		
		//Adding above created link to entity model
		//"all-users" will be key and the link will be value in JSON response returned
		entityModel.add(link.withRel("all-users"));
		
		//returning entity model
		return entityModel;
	}
	
	@DeleteMapping(path = "/jpa/users/{id}")
	public void deleteById(@PathVariable Integer id){
		repository.deleteById(id);
	}
	
	
	//Creating new user, 
	@PostMapping("/jpa/users")
	public ResponseEntity<User> createuser(@Valid @RequestBody User user) {
		User savedUser = repository.save(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()  //this will return the current request that is /users
												  .path("/{id}")         //this will append the id of the newly created user on to current requests
												  .buildAndExpand(savedUser.getId())  //this will get the value of the created or savedUser and place in the /users/{id}
												  .toUri();  //this will compile the location url as localhost:8080/users/4
		
		//return http response status 201 which stands for created and location created above to fetch the currently created user as a response
		return ResponseEntity.created(location).build();
	}
}
