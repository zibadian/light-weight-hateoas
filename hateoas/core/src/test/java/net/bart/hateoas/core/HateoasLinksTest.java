package net.bart.hateoas.core;

import net.bart.hateoas.core.builders.AbstractResourceLinkBuilder;
import net.bart.hateoas.core.builders.UrlPart;
import net.bart.hateoas.core.builders.urls.UrlPathPart;
import net.bart.hateoas.core.providers.HateoasProviderHelper;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.assertNotNull;


public class HateoasLinksTest {

    private HateoasLinks fixture;

    @BeforeClass
    public static void setupClass() {
        new HateoasProviderHelper(TestLinkBuilder.class);
    }

    @Before
    public void setup() {
        fixture = new HateoasLinks();
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