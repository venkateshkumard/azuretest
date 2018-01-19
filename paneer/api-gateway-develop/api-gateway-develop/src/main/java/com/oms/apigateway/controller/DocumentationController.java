package com.oms.apigateway.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
@EnableAutoConfiguration
public class DocumentationController implements SwaggerResourcesProvider {
    @Override
    public List<SwaggerResource> get() {
        List resources = new ArrayList();
        resources.add(swaggerResource("customer-service", "/api/customer/v2/api-docs", "2.0"));
        resources.add(swaggerResource("inventory-service", "/api/inventory/v2/api-docs", "2.0"));
        resources.add(swaggerResource("product-service", "/api/product/v2/api-docs", "2.0"));
        resources.add(swaggerResource("event-service", "/api/event/v2/api-docs", "2.0"));
        resources.add(swaggerResource("mail-service", "/api/mail/v2/api-docs", "2.0"));
        resources.add(swaggerResource("order-comp-service", "/api/order/v2/api-docs", "2.0"));
        return resources;
    }
    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

    @ResponseBody
    @RequestMapping("/swagger-resources/configuration/security")
    public ResponseEntity<SecurityConfiguration> securityConfiguration() {
        return new ResponseEntity<>(new SecurityConfiguration(null, null, null, null, null, ApiKeyVehicle.HEADER,
                "api_key", ","), HttpStatus.OK);
    }

}
