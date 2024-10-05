package com.arjun.rest.webservices.restful_web_services.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SpringSecurityConfiguration {

	//we are overriding spring security filter chain here so if we run the following function without any code written inside the function
	// except return statement, and then got to localhost:8080/jpa/users then there will be no authentication
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
//		1 All requests should be authenticated means all requests must have id and password attached to them.
		//using lambda function
		http.authorizeHttpRequests( auth -> auth.anyRequest().authenticated() );
//		2 If a request is not authenticated, a web page is shown
		http.httpBasic(withDefaults());
		
//		3 CSRF needs to be disabled
		http.csrf( csrf -> csrf.disable());
		return http.build();
	}
}
