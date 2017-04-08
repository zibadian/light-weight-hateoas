package net.bart.hateoas.core.builders.visitors;

import net.bart.hateoas.core.builders.AbstractVisitor;

import java.util.Map;

public final class MapVisitor<V> extends AbstractVisitor<Map<?, V>> {

    @Override
    public void visit(final VisitorHandler visitorHandler, final Map<?, V> object) {
        object.values().forEach(visitorHandler::visit);
    }

}
