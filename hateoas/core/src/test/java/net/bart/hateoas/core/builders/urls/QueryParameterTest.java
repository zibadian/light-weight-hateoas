package net.bart.hateoas.core.builders.urls;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class QueryParameterTest {

    private QueryParameter fixture;

    @Before
    public void setup() {
        fixture = new QueryParameter<>("test", "testValue");
    }

    @Test
    public void setup_successful() {

        assertEquals("test", fixture.getName());
        assertEquals("testValue", fixture.getValue());
        assertFalse(fixture.isMustInclude());
        assertEquals("&test=testValue", fixture.getFinalResult());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void must_include_is_set() {

        fixture.setMustInclude(true).setValue(null);

        assertEquals("test", fixture.getName());
        assertNull(fixture.getValue());
        assertTrue(fixture.isMustInclude());
        assertEquals("&test=", fixture.getFinalResult());

    }

    @SuppressWarnings("unchecked")
    @Test
    public void not_set_optional() {

        fixture.setValue(null);

        assertEquals("test", fixture.getName());
        assertNull(fixture.getValue());
        assertFalse(fixture.isMustInclude());
        assertEquals("", fixture.getFinalResult());

    }

}