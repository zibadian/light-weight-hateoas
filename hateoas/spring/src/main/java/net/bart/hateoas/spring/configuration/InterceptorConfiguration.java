package net.bart.hateoas.spring.configuration;

import net.bart.hateoas.core.configuration.HateoasConfiguration;
import net.bart.hateoas.spring.processors.SpringArgumentReturnProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
class InterceptorConfiguration extends WebMvcConfigurationSupport {

    private final RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    private final HateoasConfiguration contextConfiguration;

    InterceptorConfiguration(final RequestMappingHandlerAdapter requestMappingHandlerAdapter, final HateoasConfiguration contextConfiguration) {
        this.requestMappingHandlerAdapter = requestMappingHandlerAdapter;
        this.contextConfiguration = contextConfiguration;
    }

    @Bean
    public SpringArgumentReturnProcessor hateoasArgumentProcessor() {
        return new SpringArgumentReturnProcessor(requestMappingHandlerAdapter.getMessageConverters());
    }
}
