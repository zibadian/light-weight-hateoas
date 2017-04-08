package net.bart.hateoas.core.builders.visitors;

import net.bart.hateoas.core.builders.AbstractVisitor;

import java.util.Collection;

public final class CollectionVisitor<E> extends AbstractVisitor<Collection<E>> {

    @Override
    public void visit(final VisitorHandler visitorHandler, final Collection<E> collection) {
        collection.forEach(visitorHandler::visit);
    }

}
