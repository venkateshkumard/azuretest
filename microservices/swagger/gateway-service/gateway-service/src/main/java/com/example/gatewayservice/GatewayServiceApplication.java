package com.example.gatewayservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

import com.example.gatewayservice.filter.SimpleFilter;

import springfox.documentation.swagger.web.UiConfiguration;

@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class GatewayServiceApplication {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GatewayServiceApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}
	
	
	@Bean
	public SimpleFilter simpleFilter() {
		LOGGER.info("Inside SimpleFilter:::");
		return new SimpleFilter();
	}
	
	
	/*@Bean
	UiConfiguration uiConfig() {
		return new UiConfiguration("validatorUrl", "list", "alpha", "schema",
				UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
	}*/
}
