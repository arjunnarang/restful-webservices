package com.arjun.rest.webservices.restful_web_services.filtering;

import java.util.ArrayList;

//import static org.assertj.core.api.Assertions.filter;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
public class FilteringController {

	
	@GetMapping("/filtering")
//	public SomeBean filtering() {
//		return new SomeBean("value1","value2","value3");
//	}
	
	public MappingJacksonValue filtering() {
		
		
		SomeBean someBean = new SomeBean("value1","value2","value3");
		
		//We are using MappingJacksonValue to pass the instructions to filter the JSON response
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(someBean);
		
		
		// We created a filter using SimpleBeanPropertyFilter where we only require field1 and field3
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1","field3");
		
		//Now we passed this "filter" variable to following SimpleFilterProvider and added name "SomeBeanFilter" which matches the name in @JsonFilter in SomeBean.java
		// it creates a FilterProvided filters
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter);
		
		
		//this "filters" variable is passed to mappingJacksonValue
		mappingJacksonValue.setFilters(filters);
		return mappingJacksonValue;
	}
	
	@GetMapping("/filtering-list")
	public MappingJacksonValue filteringList() {
		
		List<SomeBean> list = new ArrayList<>();
		list.add(new SomeBean("value1","value2","value3"));
		list.add(new SomeBean("value4","value5","value6"));
		
		MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(list);
		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("field1");
		
		FilterProvider filters = new SimpleFilterProvider().addFilter("SomeBeanFilter", filter );
		
		mappingJacksonValue.setFilters(filters);
		
		return mappingJacksonValue;
	}
}
