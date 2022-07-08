package com.redhatschool.customermanagement.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {
	
	@Value("${controller.hello.name.default}")
	private String defaultName;
	
	@GetMapping
	public Map<String, String> getName(@RequestParam(required = false) String name) {
		if (name == null)
			return Map.of("name", defaultName);
		else
			return Map.of("name", name);
	}
}
