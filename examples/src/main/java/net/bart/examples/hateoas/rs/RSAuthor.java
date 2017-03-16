package net.bart.examples.hateoas.rs;

import net.bart.hateoas.core.HateoasAware;
import net.bart.hateoas.core.HateoasLinks;

import java.net.MalformedURLException;

public class RSAuthor implements HateoasAware {

    private String name;
    private HateoasLinks links = new HateoasLinks();

    public RSAuthor(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public HateoasLinks getLinks() {
        return links;
    }

    public RSAuthor addSelfLink() {
        try {
            addLink("Self", getLinks().resource(RSController.class).test(null));
        } catch (MalformedURLException e) {
        }
        return this;
    }

}
