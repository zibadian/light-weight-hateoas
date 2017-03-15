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

        final URI result = fixture
                .addPart(test)
                .addPart(new UrlPathPart("somewhere", true))
                .addPart(new FragmentPart("frag"))
                .addPart(new HostPart("localhost"))
                .build();

        assertEquals("http://localhost/somewhere?test=#frag", result.toString());
    }

    @Test
    public void add_multiple_hosts() {

        final URI result = fixture
                .addPart(new HostPart("localhost"))
                .addPart(new HostPart("testhost"))
                .build();

        assertEquals("http://testhost", result.toString());
    }

    @Test(expected = HateoasException.class)
    public void invalid_URI() {

        final URI result = fixture
                .addPart(new HostPart("localhost"))
                .addPart(new UrlPathPart("AA####AA"))
                .build();

        assertEquals("http://testhost", result.toString());
    }

}