package net.bart.hateoas.core.builders;

import net.bart.hateoas.core.HateoasException;
import net.bart.hateoas.core.builders.urls.FragmentPart;
import net.bart.hateoas.core.builders.urls.HostPart;
import net.bart.hateoas.core.builders.urls.QueryParamPart;
import net.bart.hateoas.core.builders.urls.UrlPathPart;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class LinkBuilderTest {

    private LinkBuilder fixture;

    @Before
    public void setup() {
        fixture = new LinkBuilder();
    }

    @Test
    public void build() {
        final QueryParamPart test = new QueryParamPart();
        test.add("test", "").setMustInclude(true);

        final String result = fixture
                .addPart(test)
                .addPart(new UrlPathPart("somewhere", true))
                .addPart(new FragmentPart("frag"))
                .addPart(new HostPart("localhost"))
                .build();

        assertEquals("http://localhost/somewhere?test=#frag", result);
    }

    @Test
    public void add_multiple_hosts() {

        final String result = fixture
                .addPart(new HostPart("localhost"))
                .addPart(new HostPart("testhost"))
                .build();

        assertEquals("http://testhost", result);
    }

    @Test
    public void silent_errors() {
        fixture = new LinkBuilder() {
            @Override
            public LinkBuilder addPart(final UrlPart part) {
                setErrorsDetected(true);
                return this;
            }
        };

        fixture.addPart(null);

        assertNull(fixture.build());
        assertTrue(fixture.hasErrors());
    }
}