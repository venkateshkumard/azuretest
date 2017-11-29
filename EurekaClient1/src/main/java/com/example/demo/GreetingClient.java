package com.example.demo;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("spring-cloud-eureka-client")
public interface GreetingClient {
	  @RequestMapping("/greeting")
	  String greeting(@RequestParam(value = "message", required = true)  String message);
}
