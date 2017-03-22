package net.bart.examples.hateoas.rs;

import net.bart.hateoas.core.HateoasAware;
import net.bart.hateoas.core.HateoasContext;
import net.bart.hateoas.core.HateoasLinks;
import net.bart.hateoas.core.annotations.Hateoas;

public class RSAuthor implements HateoasAware {

    private int id;
    private String name;
    private HateoasLinks links = new HateoasLinks();

    public RSAuthor(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public HateoasLinks getLinks() {
        return links;
    }

    public RSAuthor addSelfLink(@Hateoas HateoasContext context) {
        addLink("Self", getLinks().resource(RSAuthorController.class).author(id, null, null));
        return this;
    }

}
