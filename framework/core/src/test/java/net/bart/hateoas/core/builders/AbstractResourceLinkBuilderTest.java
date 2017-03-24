package net.bart.hateoas.core.builders;

import net.bart.hateoas.core.HateoasContext;
import net.bart.hateoas.core.HateoasException;
import net.bart.hateoas.core.builders.urls.UrlPathPart;
import net.bart.hateoas.core.providers.HateoasProviderHelper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AbstractResourceLinkBuilderTest {

    private AbstractResourceLinkBuilder fixture = new AbstractResourceLinkBuilder() {

        @Override
        protected UrlPart makeMethodPathPart(final Method method) {
            setErrorsDetected(errorDetected);
            return new UrlPathPart(methodpathPartMock.getHref());
        }

        @Override
        protected UrlPart makeControllerPathPart(final Class<?> resourceClass) {
            return new UrlPathPart("test");
        }

        @Override
        protected void processParameter(final Parameter parameter, final Object argument, final StringBuilder builder) {
            replaceAll(parameterNames.poll(), argument, builder);
        }

    };

    @Mock
    private UrlPart methodpathPartMock;

    private Queue<String> parameterNames = new LinkedList<>();
    private boolean errorDetected = false;

    @BeforeClass
    public static void setupClass() {
        new HateoasProviderHelper(null);
    }

    @Before
    public void setup() {
        parameterNames.add("{a}");
        parameterNames.add("{b}");
    }

    @Test
    public void succesfully_make_url() {

        fixture.getResource(TestResourceClass.class).test("", null);
        final String result = fixture.build();

        verify(methodpathPartMock).getHref();
        assertEquals(result, "/testnull");
    }

    @Test
    public void succesfully_make_url_with_replacement() {
        when(methodpathPartMock.getHref()).thenReturn("{a}/b/{a}/{b}/");

        fixture.getResource(TestResourceClass.class).test("1", null);
        final String result = fixture.build();

        verify(methodpathPartMock).getHref();
        assertEquals(result, "/test/1/b/1//");
    }

    @Test
    public void errors_detected() {
        errorDetected = true;

        fixture.getResource(TestResourceClass.class).test("", null);
        final String result = fixture.build();

        verify(methodpathPartMock).getHref();
        assertNull(result);
    }


    @Test(expected = HateoasException.class)
    public void argument_mismatch() throws Exception {

        fixture.getResource(TestResourceClass.class).test("", null);

        final Field argsField = AbstractResourceLinkBuilder.class.getDeclaredField("arguments");
        argsField.setAccessible(true);
        argsField.set(fixture, new Object[0]);

        fixture.build();

        verify(methodpathPartMock).getHref();
    }

    @Test
    public void no_path_annotation() {
        when(methodpathPartMock.getHref()).thenThrow(new NullPointerException());

        fixture.getResource(TestResourceClass.class).test("", null);

        verify(methodpathPartMock).getHref();
    }

    @Test
    public void exception_during_path_creation() {
        when(methodpathPartMock.getHref()).thenThrow(new IllegalArgumentException());

        fixture.getResource(TestResourceClass.class).test("", null);

        verify(methodpathPartMock).getHref();
    }

    public static class TestResourceClass {

        public HateoasContext test(final String test, final String test2) {
            return null;
        }
    }

}