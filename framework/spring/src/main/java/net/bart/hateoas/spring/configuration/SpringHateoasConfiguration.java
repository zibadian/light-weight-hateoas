package net.bart.hateoas.spring.configuration;

import net.bart.hateoas.core.builders.AbstractResourceLinkProvider;
import net.bart.hateoas.core.configuration.HateoasConfiguration;
import net.bart.hateoas.spring.builder.SpringResourceLinkProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(InterceptorConfiguration.class)
public class SpringHateoasConfiguration extends HateoasConfiguration {

    @Override
    protected Class<? extends AbstractResourceLinkProvider> getResourceLinkBuilderClass() {
        return SpringResourceLinkProvider.class;
    }

}
