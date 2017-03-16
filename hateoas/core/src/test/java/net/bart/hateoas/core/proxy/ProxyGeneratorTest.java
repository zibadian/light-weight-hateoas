package net.bart.hateoas.core.proxy;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ProxyGeneratorTest {

    private ProxyGenerator fixture = new ProxyGenerator();

    @Mock
    private ClassLoader failingClassLoaderMock;

    @Test
    public void successful_proxy() {

        final Class<?> result = fixture.generateProxyClass(getClass().getClassLoader(), Success.class);

        assertNotNull(result);
        assertTrue(Success.class.isAssignableFrom(result));
    }

    protected static class Success {

    }

    @Test(expected = FactoryException.class)
    public void no_constructor_proxy() {

        final Class<?> result = fixture.generateProxyClass(getClass().getClassLoader(), NoConstructor.class);

    }

    protected static class NoConstructor {
        private NoConstructor() {

        }
    }

    @Test(expected = FactoryException.class)
    public void no_default_constructor_proxy() {

        final Class<?> result = fixture.generateProxyClass(getClass().getClassLoader(), NoDefaultConstructor.class);

    }

    protected static class NoDefaultConstructor {
        protected NoDefaultConstructor(final String param) {

        }
    }

    @Test(expected = FactoryException.class)
    public void final_class_proxy() {

        final Class<?> result = fixture.generateProxyClass(getClass().getClassLoader(), FinalClass.class);

    }

    protected static final class FinalClass {

    }

    @Test
    public void exception_throwing_method() {

        final Class<?> result = fixture.generateProxyClass(getClass().getClassLoader(), ExceptionThrowingMethod.class);

        assertNotNull(result);
        assertTrue(ExceptionThrowingMethod.class.isAssignableFrom(result));
    }

    protected static class ExceptionThrowingMethod {

        public void method() throws IOException {

        }
    }

    @Test(expected = FactoryException.class)
    public void failed_to_load() throws ClassNotFoundException {

        final Class<?> result = fixture.generateProxyClass(failingClassLoaderMock, Success.class);

    }

}