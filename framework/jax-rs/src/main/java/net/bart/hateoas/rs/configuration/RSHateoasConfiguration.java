package net.bart.hateoas.rs.configuration;

import net.bart.hateoas.core.builders.AbstractResourceLinkBuilder;
import net.bart.hateoas.core.configuration.HateoasConfiguration;
import net.bart.hateoas.rs.builders.ResourceLinkBuilder;
import net.bart.hateoas.rs.providers.HateoasContextInjectionResolver;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

public class RSHateoasConfiguration extends HateoasConfiguration implements Feature {

    @Override
    public boolean configure(final FeatureContext context) {
        context.register(new HateoasContextInjectionResolver.Binder());
        return true;
    }

    @Override
    protected Class<? extends AbstractResourceLinkBuilder> getResourceLinkBuilderClass() {
        return ResourceLinkBuilder.class;
    }

}
