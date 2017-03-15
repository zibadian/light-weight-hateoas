package net.bart.hateoas.spring.configuration;

import net.bart.hateoas.core.builders.AbstractResourceLinkBuilder;
import net.bart.hateoas.core.configuration.HateoasConfiguration;
import net.bart.hateoas.spring.builder.SpringResourceLinkBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

@Configuration
@Import(InterceptorConfiguration.class)
public class SpringHateoasConfiguration extends HateoasConfiguration {

    @Override
    protected Class<? extends AbstractResourceLinkBuilder> getResourceLinkBuilderClass() {
        return SpringResourceLinkBuilder.class;
    }

}
