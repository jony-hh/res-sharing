package com.jony.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger配置
 *
 * @author jony
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi userApi() {
        String[] paths = {"/**"};
        String[] packagedToMatch = {"com.jony"};
        return GroupedOpenApi.builder().group("res-sharing")
                .pathsToMatch(paths)
                .packagesToScan(packagedToMatch).build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        Contact contact = new Contact();
        contact.setName("Jony");

        return new OpenAPI().info(new Info()
                .title("res-sharing")
                .description("高校课程思政资源共享系统")
                .contact(contact)
                .version("1.0.0")
                .termsOfService("https://github.com/res-sharing")
                .license(new License().name("MIT").url("https://github.com/res-sharing")));
    }

}