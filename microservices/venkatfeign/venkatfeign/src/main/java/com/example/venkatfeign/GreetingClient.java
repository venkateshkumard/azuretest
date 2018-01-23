package com.example.venkatfeign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("venkatfirst")
public interface GreetingClient{	
	@RequestMapping(path = "/greeting", method = RequestMethod.GET)
	public String greeting(@RequestParam(value = "message", required = true)  String message);
}