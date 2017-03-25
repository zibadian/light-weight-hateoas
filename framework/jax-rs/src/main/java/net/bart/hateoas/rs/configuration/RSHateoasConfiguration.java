package net.bart.hateoas.rs.configuration;

import net.bart.hateoas.core.builders.AbstractResourceLinkProvider;
import net.bart.hateoas.core.configuration.HateoasConfiguration;
import net.bart.hateoas.rs.builders.ResourceLinkProvider;
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
    protected Class<? extends AbstractResourceLinkProvider> getResourceLinkBuilderClass() {
        return ResourceLinkProvider.class;
    }

}
