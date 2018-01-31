package com.example.venkatfeign.api;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("venkatfirst")
interface GreetingClient{	
	 @RequestMapping(path = "/firstservice/greeting", method = RequestMethod.GET)
	 public String greeting(@RequestParam(value = "message", required = true) String message);
}
