package com.example.first;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FirstController {
	   private static final Logger LOGGER = LoggerFactory.getLogger(FirstController.class);
	
	 @GetMapping("/greeting")
	    public String greeting(@RequestParam(value = "message", required = true) String message) {
		 LOGGER.info("message={}", "After Calling FirstController greeting::"+message);
	        return "Hello  "+ message;
	    }
}
