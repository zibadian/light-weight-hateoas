package net.bart.examples.hateoas.spring;

import net.bart.hateoas.core.HateoasAware;
import net.bart.hateoas.core.HateoasLinks;

import java.net.MalformedURLException;

public class SpringAuthor implements HateoasAware {

    private String name;
    private HateoasLinks links = new HateoasLinks();

    public SpringAuthor(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public HateoasLinks getLinks() {
        return links;
    }

    public SpringAuthor addSelfLink() {
        try {
            addLink("Self", getLinks().resource(SpringController.class).test(null));
        } catch (MalformedURLException e) {
        }
        return this;
    }

}
