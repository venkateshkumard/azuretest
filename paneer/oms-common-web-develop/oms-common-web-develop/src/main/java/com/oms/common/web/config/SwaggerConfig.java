package com.oms.common.web.config;

import com.oms.common.web.controller.HomeController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * <p>Swagger specific configuration. Enables Swagger for your application and
 * configures your API Docket. In addition, a default HomeController is defined
 * to redirect requests from the "root" directory to Swagger.</p>
 * <p>
 * See the properties exposed in {@link OmsCommonWebSwaggerProperties}
 * to configure individual settings.
 */
@Configuration
@ConditionalOnProperty(value = "oms.common.web.config.swagger.enabled", matchIfMissing = true)
@EnableSwagger2
@EnableConfigurationProperties(OmsCommonWebSwaggerProperties.class)
public class SwaggerConfig {
    @Autowired
    private OmsCommonWebSwaggerProperties properties;

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfo(
                properties.getApiName(),
                properties.getApiDescription(),
                properties.getApiVersion(),
                null,
                new Contact(properties.getAuthor(), "", ""),
                null,
                null
        );
    }

    @Bean
    @ConditionalOnMissingBean(SwaggerAppParameters.class)
    public SwaggerAppParameters swaggerAppParameters() {
        return new DefaultSwaggerAppParameters();
    }

    @Bean
    @Autowired
    public Docket api(ApiInfo apiInfo, SwaggerAppParameters swaggerAppParameters) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage(properties.getControllerPackage()))
                .build()
                .apiInfo(apiInfo)
                .globalOperationParameters(swaggerAppParameters.globalOperationParameters());
    }

    @Bean
    public HomeController homeController() {
        return new HomeController();
    }

}
