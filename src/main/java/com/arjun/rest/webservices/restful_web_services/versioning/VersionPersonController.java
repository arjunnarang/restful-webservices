package com.arjun.rest.webservices.restful_web_services.versioning;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VersionPersonController {
	
	@GetMapping("/v1/person")
	private PersonV1 getFirstVersionOfPerson() {
		return new PersonV1("Bob Charlie");
	}
	
	@GetMapping("/v2/person")
	private PersonV2 getSecondVersionOfPerson() {
		return new PersonV2(new Name("Bob","Charlie"));
	}
}
