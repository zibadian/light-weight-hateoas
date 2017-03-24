package net.bart.hateoas.core.builders.urls;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

public class UrlPathPartTest {

    private UrlPathPart fixture;

    @Test
    public void null_href() {

        fixture = new UrlPathPart(null);

        assertNull(fixture.getHref());
    }

    @Test
    public void empty_href() {

        fixture = new UrlPathPart("");

        assertEquals("", fixture.getHref());
    }

    @Test
    public void must_not_include_slash() {

        fixture = new UrlPathPart("test", false);

        assertEquals("test", fixture.getHref());
    }

    @Test
    public void must_include_slash_and_already_includes() {

        fixture = new UrlPathPart("/test", true);

        assertEquals("/test", fixture.getHref());
    }

    @Test
    public void must_include_slash() {

        fixture = new UrlPathPart("test", true);

        assertEquals("/test", fixture.getHref());
    }

    @Test
    public void uniqueness() {

        fixture = new UrlPathPart("test", true);

        assertFalse(fixture.isUnique());
    }

}