package com.example.gatewayservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xmlpull.v1.XmlPullParserException;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.IOException;


@Configuration
@EnableSwagger2
public class SwaggerConfig {
  /*  @Bean
    public Docket productApi() throws IOException, XmlPullParserException {
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("com.example.gatewayservice.api"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo());

    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "Spring Boot REST API",
                "Spring Boot REST API for Online Store",
                "1.0",
                "Terms of service",
                new Contact("Panneerselvam N", "https://springframework.guru/about/", "109546@merrillcorp.com"),
                "Apache License Version 2.0",
                "https://www.apache.org/licenses/LICENSE-2.0");
        return apiInfo;
    }*/
	
	 @Bean
	    public Docket api() throws IOException, XmlPullParserException {	    
	        return new Docket(DocumentationType.SWAGGER_2)  
	          .select() 
	          .apis(RequestHandlerSelectors.basePackage("com.example.gatewayservice.api"))
	          .paths(PathSelectors.any())                          
	          .build().apiInfo(new ApiInfo("Venkatfeign Application Service Api Documentation", "Documentation automatically generated", "1.0", null, new Contact("Venkatesh", "piotrminkowski.wordpress.com", "piotr.minkowski@gmail.com"), null, null));                                           
	    }
	
	/*@Bean
    public Docket api() throws IOException, XmlPullParserException {	    
        return new Docket(DocumentationType.SWAGGER_2)  
          .select() 
          .apis(RequestHandlerSelectors.basePackage("com.example.gatewayservice.api"))
          .paths(PathSelectors.any())                          
          .build().apiInfo(new ApiInfo("Api Gateway Service Api Documentation", "Documentation automatically generated", "1.0", null, new Contact("Venkatesh", "piotrminkowski.wordpress.com", "piotr.minkowski@gmail.com"), null, null, null));                                           
    }*/
}
