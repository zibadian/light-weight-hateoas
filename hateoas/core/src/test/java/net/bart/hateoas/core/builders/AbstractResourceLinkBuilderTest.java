package net.bart.hateoas.core.builders;

import net.bart.hateoas.core.HateoasContext;
import net.bart.hateoas.core.builders.urls.UrlPathPart;
import net.bart.hateoas.core.providers.HateoasProviderHelper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Method;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AbstractResourceLinkBuilderTest {

    private AbstractResourceLinkBuilder fixture = new AbstractResourceLinkBuilder() {
        @Override
        protected UrlPart makeMethodPathPart(final Method method) {
            return new UrlPathPart(methodpathPartMock.getHref());
        }

        @Override
        protected UrlPart makeControllerPathPart(final Class<?> resourceClass) {
            return new UrlPathPart("test");
        }
    };

    @Mock
    private UrlPart methodpathPartMock;

    @BeforeClass
    public static void setupClass() {
        new HateoasProviderHelper(null);
    }

    @Test
    public void succesfully_make_url() {

        fixture.getResource(TestResourceClass.class).test();

        verify(methodpathPartMock).getHref();
    }

    @Test
    public void no_path_annotation() {
        when(methodpathPartMock.getHref()).thenThrow(new NullPointerException());

        fixture.getResource(TestResourceClass.class).test();

        verify(methodpathPartMock).getHref();
    }

    @Test
    public void exception_during_path_creation() {
        when(methodpathPartMock.getHref()).thenThrow(new IllegalArgumentException());

        fixture.getResource(TestResourceClass.class).test();

        verify(methodpathPartMock).getHref();
    }

    public static class TestResourceClass {

        public HateoasContext test() {
            return null;
        }
    }

}