//@Bean layer

package com.arjun.rest.webservices.restful_web_services.user;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

@Entity(name="user_details")
//renaming the table user table to "user_details" as user is a keyword in h2 database
public class User {

	//A default constructor is needed when connecting with JPA 
	protected User(){}
	
	@Id
	@GeneratedValue
	private Integer id;
	
	@OneToMany(mappedBy="user")  //user is the object created in Post.java
	@JsonIgnore
	private List<Post> posts;   //a list of type "Post" is created
	
	@Size(min=2, message="Name should have atleast 2 characters")
	@JsonProperty("user_name")
	private String name;
	
	@Past(message="Birthdate should be of past")
	@JsonProperty("birth_date")
	private LocalDate birthdate;
	
	public User(Integer id, String name, LocalDate birthdate) {
		super();
		this.id = id;
		this.name = name;
		this.birthdate = birthdate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(LocalDate birthdate) {
		this.birthdate = birthdate;
	}

	

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", birthdate=" + birthdate + "]";
	}
	
	
	
}
