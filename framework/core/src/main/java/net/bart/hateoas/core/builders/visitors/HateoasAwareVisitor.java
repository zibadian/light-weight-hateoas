package net.bart.hateoas.core.builders.visitors;

import net.bart.hateoas.core.HateoasAware;
import net.bart.hateoas.core.builders.AbstractVisitor;

public class HateoasAwareVisitor<T extends HateoasAware> extends AbstractVisitor<T> {

    @Override
    public void visit(final VisitorHandler visitorHandler, final T object) {
        createSelfLink(object);
        createAdditionalLinks(object);
    }

    protected void createAdditionalLinks(final T object) {

    }

    private void createSelfLink(final HateoasAware object) {
        object.getLinks().addLink("self", object.createSelfLink());
    }

    @Override
    public final boolean hasFieldsToVisit(final Object object) {
        return true;
    }
}
