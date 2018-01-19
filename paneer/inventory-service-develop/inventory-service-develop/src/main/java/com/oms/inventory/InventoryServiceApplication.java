package com.oms.inventory;

import com.oms.common.web.EnableOMSCommonWeb;
import org.springframework.boot.SpringApplication;
import com.oms.common.security.config.EnableOMSSecurity;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableOMSCommonWeb
@EnableEurekaClient
@EnableOMSSecurity
public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}
}
