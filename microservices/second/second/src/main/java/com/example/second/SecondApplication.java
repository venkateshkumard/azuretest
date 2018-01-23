package com.example.second;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAutoConfiguration
@EnableEurekaClient
public class SecondApplication {


	 @LoadBalanced
	  @Bean
	  RestTemplate restTemplate(){
	    return new RestTemplate();
	  }
	 
	public static void main(String[] args) {
		SpringApplication.run(SecondApplication.class, args);
	}
}
