package net.bart.hateoas.core.builders;

import net.bart.hateoas.core.providers.HateoasProviderHelper;

import java.net.URI;

public abstract class AbstractLinkBuilder {

    private final String ref;

    public AbstractLinkBuilder(final String ref) {
        this.ref = ref;
    }

    public String getRef() {
        return ref;
    }

    public abstract URI build();

    protected final URI getBaseUri() {
        return (URI) HateoasProviderHelper.getLinkProperty(HateoasProviderHelper.BASE_URI_KEY);
    }

}
