package com.oms.product;

import com.oms.common.security.config.EnableOMSSecurity;
import com.oms.common.web.EnableOMSCommonWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
//@EnableCircuitBreaker
@EnableOMSCommonWeb
@EnableOMSSecurity
public class ProductServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductServiceApplication.class, args);
    }
}

