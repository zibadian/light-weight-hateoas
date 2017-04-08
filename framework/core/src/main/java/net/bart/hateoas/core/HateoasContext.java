package net.bart.hateoas.core;

import net.bart.hateoas.core.providers.LinkProvider;

public class HateoasContext implements HateoasAware {

    private Object content;
    private HateoasLinks links = new HateoasLinks();
    private final LinkProvider self;

    public HateoasContext(final LinkProvider self) {
        this.self = self;
    }

    public HateoasContext content(final Object content) {
        this.content = content;
        return this;
    }

    public Object getContent() {
        return content;
    }

    @Override
    public final Object createSelfLink() {
        return self.provideSelfLink().build();
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

    public HateoasContext addSelfLinks() {
        links.addLink("self", createSelfLink());
        return this;
    }

}
