package net.bart.hateoas.core;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HateoasAwareTest {

    private HateoasAware fixture;

    @Test
    public void add_link() {
        final HateoasLinks result = new HateoasLinks();

        fixture = new HateoasAware() {
            @Override
            public HateoasLinks getLinks() {
                return result;
            }
        };

        fixture.addLink("test", "test");

        assertEquals(1, result.getLinks().size());
        assertEquals("test", result.getLinks().get("test"));
    }
}