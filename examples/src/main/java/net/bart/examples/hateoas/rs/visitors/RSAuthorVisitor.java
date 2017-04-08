package net.bart.examples.hateoas.rs.visitors;

import net.bart.examples.hateoas.rs.RSAuthor;
import net.bart.hateoas.core.builders.visitors.HateoasAwareVisitor;

public class RSAuthorVisitor extends HateoasAwareVisitor<RSAuthor> {

    @Override
    protected void createAdditionalLinks(final RSAuthor object) {
        object.addLink("books", "/api/books/?author=" + object.getId());
    }

}
