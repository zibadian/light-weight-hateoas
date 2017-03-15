package net.bart.hateoas.core;

import net.bart.hateoas.core.builders.LinkBuilder;
import net.bart.hateoas.core.providers.LinkProvider;

public class HateoasContext implements HateoasAware {

    private Object content;
    private HateoasLinks links = new HateoasLinks();
    private LinkProvider self;

    public HateoasContext content(final Object content) {
        this.content = content;
        return this;
    }

    public Object getContent() {
        return content;
    }

    public HateoasContext setSelf(final LinkProvider self) {
        this.self = self;
        return this;
    }

    public HateoasContext addSelfLink() {
        final LinkBuilder abstractLinkBuilder = self.provideSelfLink();
        addLink("self", abstractLinkBuilder.build());
        return this;
    }

    @Override
    public HateoasContext addLink(final String ref, final Object link) {
        links.addLink(ref, link);
        return this;
    }

    public <T> T resource(final Class<T> resourceClass) {
        return links.resource(resourceClass);
    }

    @Override
    public HateoasLinks getLinks() {
        return links;
    }

}
