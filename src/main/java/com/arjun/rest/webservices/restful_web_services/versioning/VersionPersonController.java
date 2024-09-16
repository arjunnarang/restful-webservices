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
	
	//------------------------------------------------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------------------------------
	
	//Parameter type versioning 1
	//Hit this function only when url contains "version=1" as params
	@GetMapping(path="/person", params="version=1")
	private PersonV1 getFirstVersionOfPersonRequestParamter() {
		return new PersonV1("Bob Charlie");
	}
	
	//Parameter type versioning 2
	@GetMapping(path="/person", params="version=2")
	private PersonV2 getSecondVersionOfPersonOfRequestParameter() {
		return new PersonV2(new Name("Bob","Charlie"));
	}
	//------------------------------------------------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------------------------------
	
	//Headers type versioning 1
	@GetMapping(path="/person/headers", headers="X-API-VERSION=1")
	private PersonV1 getFirstVersionOfPersonRequestHeader() {
		
		// Add "X-API-VERSION" as header in api testing client and value as "1"
		
		return new PersonV1("Bob Charlie");
	}
	
	//Headers type versioning 2
	@GetMapping(path="/person/headers", headers="X-API-VERSION=2")
	private PersonV2 getSecondVersionOfPersonOfHeaderParameter() {
		
		// Add "X-API-VERSION" as header in api testing client and value as "2"
		
		return new PersonV2(new Name("Bob","Charlie"));
	}
	
	//------------------------------------------------------------------------------------------------------------------------
	//------------------------------------------------------------------------------------------------------------------------

	//Content negotiation(Accept-header) type versioning 1
	@GetMapping(path="/person/accept", produces="application/vnd.company.app-v1+json")
	////here we use "produces" to capture and match the content of accept header and the content mentioned here
	
	// Select headers as "ACCEPT" in talend api tester and put above content as value and hit the url
	
	private PersonV1 getFirstVersionOfPersonAcceptHeader() {
		return new PersonV1("Bob Charlie");
	}
		
	//Content negotiation(Accept-header) type versioning 2
	@GetMapping(path="/person/accept", produces="application/vnd.company.app-v2+json")  
	//here we use "produces" to capture and match the content of accept header and the content mentioned here
	
	private PersonV2 getSecondVersionOfPersonOfAcceptParameter() {
		return new PersonV2(new Name("Bob","Charlie"));
	}
}
