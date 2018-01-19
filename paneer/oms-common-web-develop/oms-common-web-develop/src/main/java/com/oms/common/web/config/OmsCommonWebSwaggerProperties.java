package com.oms.common.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for swagger settings. These settings should
 * be discoverable by your IDE for content assist as well.
 */
@ConfigurationProperties("oms.common.web.config.swagger")
public class OmsCommonWebSwaggerProperties {
    /**
     * Swagger config is enabled by default. Use this to disable it entirely.
     * Apps will be responsible for defining their @EnableSwagger and misc.
     * configuration themselves
     */
    private boolean enabled;
    /**
     * Base package to scan for Spring Controllers. Ideally this should just
     * be set to your controller package directly to avoid pulling in any
     * extra autoconfigured controllers.
     */
    private String controllerPackage = "com.oms";
    /**
     * "Author" of the API, as displayed at the top of your Swagger documentation
     */
    private String author = "Merrill Corporation";
    /**
     * Short name of your API. Try to keep this to the microservice name
     */
    private String apiName = "Default API name";
    /**
     * Longer description of your API. Include likely use cases or describe
     * collaborating services here as well.
     */
    private String apiDescription = "This API Needs a description!";
    /**
     * Version of the API, noted at the bottom of the page. This version has no
     * direct influence on any API wiring, it is purely informational.
     */
    private String apiVersion = "v1";

    public boolean isEnabled() {
        return enabled;
    }

    public OmsCommonWebSwaggerProperties setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getControllerPackage() {
        return controllerPackage;
    }

    public OmsCommonWebSwaggerProperties setControllerPackage(String controllerPackage) {
        this.controllerPackage = controllerPackage;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public OmsCommonWebSwaggerProperties setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getApiName() {
        return apiName;
    }

    public OmsCommonWebSwaggerProperties setApiName(String apiName) {
        this.apiName = apiName;
        return this;
    }

    public String getApiDescription() {
        return apiDescription;
    }

    public OmsCommonWebSwaggerProperties setApiDescription(String apiDescription) {
        this.apiDescription = apiDescription;
        return this;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public OmsCommonWebSwaggerProperties setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
        return this;
    }
}
