package net.bart.hateoas.core.providers;

import net.bart.hateoas.core.HateoasContext;
import net.bart.hateoas.core.builders.AbstractResourceLinkBuilder;
import net.bart.hateoas.core.builders.UrlPart;
import net.bart.hateoas.core.builders.urls.UrlPathPart;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import java.lang.reflect.Method;

import static org.junit.Assert.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
public class HateoasProviderHelperTest {

    @BeforeClass
    public static void setupClass() {
        new HateoasProviderHelper(TestLinkBuilder.class);
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

    public static class TestResourceClass {

        public HateoasContext test() {
            return null;
        }
    }

    public static class ErrorTestResourceClass {

        public ErrorTestResourceClass() {
            throw new RuntimeException();
        }

    }

    @Test
    public void make_watcher() {

       TestResourceClass result = HateoasProviderHelper.makeResourceWatcher(TestResourceClass.class, null);

        assertNotNull(result);
    }

    @Test(expected = ClassCastException.class)
    public void make_watcher_from_object() {

        HateoasProviderHelper.makeResourceWatcher(Object.class, null);

    }

    @Test (expected = RuntimeException.class)
    public void error_during_instantiation_of_class() {

        HateoasProviderHelper.makeResourceWatcher(ErrorTestResourceClass.class, null);

    }

}