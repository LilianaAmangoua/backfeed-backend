package com.backfeed.backfeed_core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("BackFeed API")
                        .version("1.0")
                        .description("BackFeed is a collaborative platform designed for product owners \n" +
                                "                    to manage customer feedback, report bugs, and track feature requests.\n" +
                                "\n" +
                                "                    This API provides access to all core functionalities, including:\n" +
                                "                    - User registration and authentication\n" +
                                "                    - Role-based access (PO, Client, Developer, Super Admin)\n" +
                                "                    - Invitation management and hierarchy tracking\n" +
                                "                    - Feedback and issue tracking features\n" +
                                "\n" +
                                "                    The API is secured using JWT tokens. Authentication is required for most endpoints."));
    }
}
