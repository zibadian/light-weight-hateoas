package net.bart.hateoas.spring.configuration;

import net.bart.hateoas.spring.processors.SpringArgumentReturnProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
class InterceptorConfiguration extends WebMvcConfigurationSupport {

    @Bean
    @Autowired
    public SpringArgumentReturnProcessor hateoasArgumentProcessor(RequestMappingHandlerAdapter requestMappingHandlerAdapter) {
        return new SpringArgumentReturnProcessor(requestMappingHandlerAdapter.getMessageConverters());
    }

}
