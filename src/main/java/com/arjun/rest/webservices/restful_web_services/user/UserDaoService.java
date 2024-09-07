//Business layer where all the logic is written 
//hence @Component is mentioned

package com.arjun.rest.webservices.restful_web_services.user;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.stereotype.Component;



@Component
public class UserDaoService {

	
	private static List<User> users =new ArrayList<>();
	
	static {
		users.add(new User(1, "Adam", LocalDate.now().minusYears(30)));
		users.add(new User(2, "Eve", LocalDate.now().minusYears(29)));
		users.add(new User(3, "Son", LocalDate.now().minusYears(28)));
		
	}
	
	private static int usersCount = 3;
	//public List<User> findAll()
	public List<User> findAll(){
		return users;
	}
	//public User save(User user)
	
	public User save(User user) {
		user.setId(++usersCount);
		users.add(user);
		return user;
	}
	
	//public User findOne(int id)
	
	public User findOne(int id) {
		Predicate<? super User> predicate = user -> user.getId().equals(id);
		
		return users.stream().filter(predicate).findFirst().orElse(null);
	}
}
