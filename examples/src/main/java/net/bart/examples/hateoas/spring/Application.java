package net.bart.examples.hateoas.spring;

import net.bart.hateoas.spring.configuration.SpringHateoasConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Collections;

@EnableAutoConfiguration
@Import({SpringHateoasConfiguration.class})
public class Application {

    public static void main(String[] args) {
        new SpringApplicationBuilder()
                .sources(Application.class)
                .properties(Collections.singletonMap("server.port", 9999))
                .run(args);
    }

    @Bean
    public SpringController springController() {
        return new SpringController();
    }
}
