package net.bart.hateoas.core.configuration;

import net.bart.hateoas.core.builders.AbstractResourceLinkProvider;
import net.bart.hateoas.core.builders.UrlPart;
import net.bart.hateoas.core.providers.HateoasProviderHelper;

public abstract class HateoasConfiguration {

    public HateoasConfiguration() {
        new HateoasProviderHelper(getResourceLinkBuilderClass());
    }

    protected abstract Class<? extends AbstractResourceLinkProvider> getResourceLinkBuilderClass();


    public HateoasConfiguration setBaseUri(final UrlPart baseUri) {
        HateoasProviderHelper.setLinkProperty(HateoasProviderHelper.BASE_URI_KEY, baseUri);
        return this;
    }
}
