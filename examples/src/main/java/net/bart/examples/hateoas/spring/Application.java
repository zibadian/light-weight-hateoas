package net.bart.examples.hateoas.spring;

import net.bart.hateoas.spring.configuration.SpringHateoasConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@EnableAutoConfiguration
@Import({SpringHateoasConfiguration.class})
public class Application {

    public static void main(String[] args) {
        ApplicationContext c = SpringApplication.run(Application.class, args);
    }

    @Bean
    public SpringController springController() {
        return new SpringController();
    }
}
