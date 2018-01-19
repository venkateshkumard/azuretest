package com.oms.billing;

import com.oms.common.security.config.EnableOMSSecurity;
import com.oms.common.web.EnableOMSCommonWeb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableOMSCommonWeb
@EnableOMSSecurity
@EnableEurekaClient
public class BillingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}
}
