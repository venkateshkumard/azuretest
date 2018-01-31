package com.example.venkatfeign.config;

import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

import java.io.IOException;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
   /* @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("com.example.venkatfeign.api"))
                .paths(regex("/venkatfeign.*"))
                .build()
                .apiInfo(apiInfo());

    }*/
	
	 @Bean
	    public Docket api() throws IOException, XmlPullParserException {	    
	        return new Docket(DocumentationType.SWAGGER_2)  
	          .select() 
	          .apis(RequestHandlerSelectors.basePackage("com.example.venkatfeign.api"))
	          //.paths(regex("/venkatfeign.*"))
	          .paths(PathSelectors.any())                          
	          .build().apiInfo(new ApiInfo("VenkatfeignApplication Service Api Documentation", "Documentation automatically generated", "1.0", null, new Contact("Venkatesh", "piotrminkowski.wordpress.com", "piotr.minkowski@gmail.com"), null, null));                                           
	    }

   /* private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Spring Boot REST API",
                "Spring Boot REST API for Feign Client",
                "1.0",
                "Terms of service",
                new Contact("Venkatesh Kumar D", "https://springframework.guru/about/", "109988@merrillcorp.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
        return apiInfo;
    }*/
}
