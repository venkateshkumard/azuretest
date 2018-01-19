package com.oms.common.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Common home controller providing basic behavior to any microservice.
 * <p>
 * Provides common forwarding/redirect functionality to allow our services to behave the same way, including:
 * <ul>
 * <li>Redirect requests for root (/) to the Swagger UI</li>
 * <li>Forward requests to an arbitrary documentation endpoint (/swagger/*) to the Swagger API docs to allow the
 * API Gateway to compose documentation from multiple composite services</li>
 * </ul>
 */
@Controller
@ApiIgnore
public class HomeController {
    @RequestMapping("/")
    public String redirectToSwaggerUi() {
        return "redirect:/swagger-ui.html";
    }

    @RequestMapping("/swagger/{appName}")
    public String forwardToSwaggerDefinition() {
        return "forward:/v2/api-docs";
    }
}
