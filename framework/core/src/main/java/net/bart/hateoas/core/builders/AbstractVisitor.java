package net.bart.hateoas.core.builders;

import net.bart.hateoas.core.builders.visitors.VisitorHandler;

public abstract class AbstractVisitor<T> {

    public abstract void visit(final VisitorHandler visitorHandler, final T object);

    public boolean hasFieldsToVisit(final Object object) {
        return !object.getClass().getCanonicalName().startsWith("java");
    }

}
