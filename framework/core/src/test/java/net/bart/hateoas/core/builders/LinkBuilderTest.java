package net.bart.hateoas.core.builders;

import net.bart.hateoas.core.builders.urls.UrlPathPart;
import org.junit.Before;
import org.junit.Test;

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
        final String result = fixture
                .addPart(new UrlPathPart("somewhere", true))
                .build();

        assertEquals("/somewhere", result);
    }

    @Test
    public void silent_errors() {
        fixture = new TestLinkBuilder();

        fixture.addPart(new UrlPathPart(""));

        assertNull(fixture.build());
        assertTrue(fixture.hasErrors());
    }

    private class TestLinkBuilder extends LinkBuilder {
        TestLinkBuilder() {
            setErrorsDetected(true);
        }
    }

}