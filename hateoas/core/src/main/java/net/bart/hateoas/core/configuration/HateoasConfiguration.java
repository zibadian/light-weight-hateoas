package net.bart.hateoas.core.configuration;

import net.bart.hateoas.core.builders.AbstractResourceLinkBuilder;
import net.bart.hateoas.core.providers.HateoasProviderHelper;

import java.net.URI;

public abstract class HateoasConfiguration {

    public HateoasConfiguration() {
        new HateoasProviderHelper(getResourceLinkBuilderClass());
    }

    protected abstract Class<? extends AbstractResourceLinkBuilder> getResourceLinkBuilderClass();


    public HateoasConfiguration setBaseUri(final URI baseUri) {
        HateoasProviderHelper.setLinkProperty(HateoasProviderHelper.BASE_URI_KEY, baseUri);
        return this;
    }
}
