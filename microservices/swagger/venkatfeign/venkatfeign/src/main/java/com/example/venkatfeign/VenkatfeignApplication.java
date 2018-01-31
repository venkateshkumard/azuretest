package com.example.venkatfeign;

import static springfox.documentation.builders.PathSelectors.regex;

import java.io.FileReader;
import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;



import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*@SpringBootApplication
@EnableAutoConfiguration
@EnableEurekaClient
@EnableFeignClients
@EnableSwagger2*/

@SpringBootApplication
@EnableAutoConfiguration
//@EnableEurekaClient
@EnableDiscoveryClient
@EnableFeignClients
//@EnableSwagger2
public class VenkatfeignApplication {

	public static void main(String[] args) {
		SpringApplication.run(VenkatfeignApplication.class, args);
	}
	

	/*
	 @Bean
	    public Docket api() throws IOException, XmlPullParserException {	    
	        return new Docket(DocumentationType.SWAGGER_2)  
	          .select() 
	          .apis(RequestHandlerSelectors.basePackage("com.example.venkatfeign.api"))
	          .paths(regex("/venkatfeign.*"))
	          //.paths(PathSelectors.any())                          
	          .build().apiInfo(new ApiInfo("VenkatfeignApplication Service Api Documentation", "Documentation automatically generated", "1.0", null, new Contact("Venkatesh", "piotrminkowski.wordpress.com", "piotr.minkowski@gmail.com"), null, null));                                           
	    }*/
	
	

	
}
