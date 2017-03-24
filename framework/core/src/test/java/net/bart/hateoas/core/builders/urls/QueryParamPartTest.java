package net.bart.hateoas.core.builders.urls;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class QueryParamPartTest {

    private QueryParamPart fixture;

    @Before
    public void setup() {
        fixture = new QueryParamPart();
    }

    @Test
    public void no_parameters() throws Exception {

        assertEquals("", fixture.getHref());
        assertTrue(fixture.getParameters().isEmpty());
    }

    @Test
    public void non_empty_parameters() throws Exception {

        fixture.add("test", 1234);
        assertEquals("?test=1234", fixture.getHref());
    }

    @Test
    public void empty_parameters() throws Exception {

        fixture.add("test", null);
        assertEquals("", fixture.getHref());
    }

    @Test
    public void must_include_non_empty() throws Exception {

        fixture.add("test", "1234").setMustInclude(true);
        assertEquals("?test=1234", fixture.getHref());
    }

    @Test
    public void must_include_empty() throws Exception {

        fixture.add("test", null).setMustInclude(true);
        assertEquals("?test=", fixture.getHref());
    }

    @Test
    public void multiple_parameters() throws Exception {

        fixture.add("test1", null).setMustInclude(true);
        fixture.add("test2", "1234");
        fixture.add("test3", 9);
        fixture.add("test4", true);
        fixture.add("test5", null);

        final String result = fixture.getHref();

        assertTrue(result.contains("test1="));
        assertTrue(result.contains("test2=1234"));
        assertTrue(result.contains("test3=9"));
        assertTrue(result.contains("test4=true"));
        assertFalse(result.contains("test5="));
    }

}