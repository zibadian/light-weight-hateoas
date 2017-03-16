package net.bart.hateoas.core;

import net.bart.hateoas.core.builders.AbstractResourceLinkBuilder;
import net.bart.hateoas.core.builders.LinkBuilder;
import net.bart.hateoas.core.builders.UrlPart;
import net.bart.hateoas.core.builders.urls.UrlPathPart;
import net.bart.hateoas.core.providers.HateoasProviderHelper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class HateoasLinksTest {

    private HateoasLinks fixture;
    private Map<String, URI> links;

    @BeforeClass
    public static void setupClass() {
        new HateoasProviderHelper(TestLinkBuilder.class);
    }

    @Before
    @SuppressWarnings("unchecked")
    public void setup() throws NoSuchFieldException, IllegalAccessException {
        fixture = new HateoasLinks();

        final Field linksField = fixture.getClass().getDeclaredField("links");
        linksField.setAccessible(true);
        links = (Map<String, URI>) linksField.get(fixture);
    }

    @Test
    public void empty_links() {

        final Map<String, String> result = fixture.getLinks();

        assertNull(result);
    }

    @Test
    public void with_links() {
        fixture.addLink("test", "/test");

        final Map<String, String> result = fixture.getLinks();

        assertEquals(1, result.size());
        assertEquals("/test", result.get("test"));
    }

    @Test
    public void null_value_in_links() throws NoSuchFieldException, IllegalAccessException {
        links.put("test", null);

        final Map<String, String> result = fixture.getLinks();

        assertNull(result);
    }

    @Test
    public void add_null_link() {
        fixture.addLink("test", null);

        assertFalse(links.containsKey("test"));
    }

    @Test
    public void add_URI_link() throws URISyntaxException {
        final URI expected = new URI("/test");

        fixture.addLink("test", expected);

        assertTrue(links.containsKey("test"));
        assertEquals(expected, links.get("test"));
    }

    @Test
    public void add_linkBuilder() throws URISyntaxException {
        final URI expected = new URI("/test");
        final LinkBuilder builder = new LinkBuilder().addPart(new UrlPathPart("/test"));

        fixture.addLink("test", builder);

        assertTrue(links.containsKey("test"));
        assertEquals(expected, links.get("test"));
    }

    @Test
    public void add_string() throws URISyntaxException {
        final URI expected = new URI("/test");

        fixture.addLink("test", "/test");

        assertTrue(links.containsKey("test"));
        assertEquals(expected, links.get("test"));
    }

    @Test(expected = HateoasException.class)
    public void add_invalid_uri() throws URISyntaxException {
        fixture.addLink("test", "://test");
    }

    @Test
    public void add_via_internal_link() throws Exception {
        final Field iLinkField = fixture.getClass().getDeclaredField("internalLink");
        iLinkField.setAccessible(true);

        iLinkField.set(fixture, new AbstractResourceLinkBuilder() {
            @Override
            protected UrlPart makeMethodPathPart(final Method method) {
                return null;
            }

            @Override
            protected UrlPart makeControllerPathPart(final Class<?> resourceClass) {
                return null;
            }
        });
        fixture.addLink("test", null);

        assertTrue(links.containsKey("test"));
    }

    @Test
    public void build_resource_proxy() {

        HateoasLinksTest result = fixture.resource(HateoasLinksTest.class);

        assertNotNull(result);

    }

    public static class TestLinkBuilder extends AbstractResourceLinkBuilder {

        @Override
        protected UrlPart makeMethodPathPart(Method method) {
            return new UrlPathPart("testMethod");
        }

        @Override
        protected UrlPart makeControllerPathPart(Class<?> resourceClass) {
            return new UrlPathPart("testResource");
        }
    }

}