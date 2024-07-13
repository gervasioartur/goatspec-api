package com.goatspec.main.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
    @Value("${spring.application.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Value("${app.environment}")
    private String appEnvironment;

    @Value("${app.server.url}")
    private String appServerUrl;

    @Bean
    public OpenAPI openAPI() {
        String description = "A GOAT Soluções Digitais precisa desenvolver um sistema no qual os servidores de\n" +
                "uma instituição pública se cadastrem e que eles possam cadastrar suas solicitações de\n" +
                "especialização, você foi escolhido para fazer a API Rest no backend.\n";
        return new OpenAPI()
                .info(new Info()
                        .title(this.appName.toUpperCase() + " - " + this.appEnvironment.toLowerCase())
                        .version(this.appVersion)
                        .description(description))
                .servers(Collections.singletonList(
                        new Server().url(appServerUrl).description("Default url")
                ));
    }
}
