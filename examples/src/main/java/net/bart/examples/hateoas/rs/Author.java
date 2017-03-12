package net.bart.examples.hateoas.rs;

import net.bart.hateoas.core.HateoasAware;
import net.bart.hateoas.core.HateoasLinks;

import java.net.MalformedURLException;

public class Author implements HateoasAware {

    private String name;
    private HateoasLinks links = new HateoasLinks();

    public Author(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public HateoasLinks getLinks() {
        return links;
    }

    public Author addSelfLink() {
        try {
            addLink("Self", getLinks().resource(BootController.class).test(null));
        } catch (MalformedURLException e) {
        }
        return this;
    }

}
