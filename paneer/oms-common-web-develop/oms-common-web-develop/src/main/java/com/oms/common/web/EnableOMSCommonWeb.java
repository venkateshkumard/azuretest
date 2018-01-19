package com.oms.common.web;

import com.oms.common.web.config.SwaggerConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Enable OMS common web configuration, including items such as:
 * <ul>
 * <li>Automatic HomeController redirecting / to swagger</li>
 * <li>Swagger Configuration?</li>
 * </ul>
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(SwaggerConfig.class)
public @interface EnableOMSCommonWeb {
}
