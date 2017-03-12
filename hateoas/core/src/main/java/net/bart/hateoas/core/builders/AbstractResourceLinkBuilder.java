package net.bart.hateoas.core.builders;

import java.net.URI;

public abstract class AbstractResourceLinkBuilder extends AbstractLinkBuilder {

    private URI internalLink;

    public AbstractResourceLinkBuilder() {
        super("");
    }

    @Override
    public final URI build() {
        return internalLink;
    }

    protected final void setInternalLink(final URI internalLink) {
        this.internalLink = internalLink;
    }

    public abstract <T> T getResource(final Class<T> resourceClass);

}
