package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@RestController
public class EurekaClient1Application {
    @Autowired
    private GreetingClient greetingClient;
 
    public static void main(String[] args) {
        SpringApplication.run(EurekaClient1Application.class, args);
      
    }
 
    @RequestMapping("/get-greeting")
    public String greeting(@RequestParam(value = "message", required = true) String message) {
      /*  model.addAttribute("greeting", greetingClient.greeting());
        return "greeting-view";*/
    	return greetingClient.greeting(message);
    }
}
