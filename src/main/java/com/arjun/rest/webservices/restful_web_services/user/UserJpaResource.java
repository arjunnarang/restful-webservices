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

import com.arjun.rest.webservices.restful_web_services.jpa.PostRepository;
import com.arjun.rest.webservices.restful_web_services.jpa.UserRepository;

import jakarta.validation.Valid;

@RestController
public class UserJpaResource {

	//Using constructor injection as UserDaoService is handled by spring as all the business logic is written there	
	private UserRepository repository;
	
	private PostRepository postRepository;
	
	public UserJpaResource(UserRepository repository, PostRepository postRepository) {
		this.repository = repository;
		this.postRepository = postRepository;
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
	
	
	
	//Fetching all posts for a particular user
	
	@GetMapping(path = "/jpa/users/{id}/posts")
	public List<Post> retrievePostsForUser(@PathVariable int id){
		
		//first we found the user by id
		Optional<User> user = repository.findById(id);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("id:" + id);
		}
		
		
		//now we have a user stored in the "user" object and now through JPA we retrieved posts linked with 
		//current user id. Spring boot would automatically fetch posts of that particular user because a user_id column is created in the post table
		//because in User.java we have mentioned "user" as the relationship owner between User and Post
		return user.get().getPosts();
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
	
	
	//Function to create posts for a specific user
	@PostMapping(path = "/jpa/users/{id}/posts")
	public ResponseEntity<Object> createPostForUser(@PathVariable int id, @Valid @RequestBody Post post){
		
		//first we found the user by id
		Optional<User> user = repository.findById(id);
		
		if(user.isEmpty()) {
			throw new UserNotFoundException("id:" + id);
		}
		
		//We set the user in User type variable in Post so that post is connected with the user
		post.setUser(user.get());
		
		//then we add the post into the list
		Post savedPost = postRepository.save(post);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest()  //this will return the current request that is /posts
				  								  .path("/{id}")         //this will append the id of the newly created post on to current requests
				  								  .buildAndExpand(savedPost.getId())  //this will get the value of the created or savedPost and place in the /users/{id}
				  								  .toUri();  //this will compile the location url as localhost:8080/jpa/users/${id}/posts/1

			//return http response status 201 which stands for created and location created above to fetch the currently created user as a response
			return ResponseEntity.created(location).build();
		
	}
	
	
	@GetMapping(path = "/jpa/users/{id}/posts/{postId}")
	//Adding entitymodel here so as to implement hateoas means adding links to other URLs in JSON responses 
//	{
//		"description": "I want to learn Java",
//		"_links":{
//		"all-posts":{
//		"href": "http://localhost:8080/jpa/users/1001/posts"
//		}
//		}
//		}
	
//	 We can see the we can directly go to all posts link by clicking on href link
//	mentioned in above response
	public EntityModel<Post> retrievePostById(@PathVariable Integer id, @PathVariable Integer postId){
		Optional<Post> post = postRepository.findById(postId);
		
		if(post.isEmpty()) {
			throw new PostNotFoundException("id:" + postId);
		}
		
		EntityModel<Post> entityModel = EntityModel.of(post.get());
		
		//this creates a route or link to retrieve all posts using WebMvcLinkBuilder
		WebMvcLinkBuilder link = linkTo(methodOn(this.getClass()).retrievePostsForUser(id));
		
		//Adding above created link to entity model
		//"all-posts" will be key and the link will be value in JSON response returned
		entityModel.add(link.withRel("all-posts"));
		
		//returning entity model
		return entityModel;
	}
}
