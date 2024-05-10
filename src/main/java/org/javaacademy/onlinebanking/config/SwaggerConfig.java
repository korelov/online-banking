package org.javaacademy.onlinebanking.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Server eurobank = new Server();
        eurobank.setUrl("http://localhost:8003");
        eurobank.setDescription("LLC Eurobank");

        Server moneybank = new Server();
        moneybank.setUrl("http://localhost:8082");
        moneybank.setDescription("LLC Moneybank");

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Online Banking Api")
                                .version("1.0.0")
                                .contact(
                                        new Contact()
                                                .email("korelov@gmail.com")
                                                .url("https://github.com/korelov")
                                                .name("Корелов Максим")
                                )
                ).servers(List.of(eurobank, moneybank));
    }
}
