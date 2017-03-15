package net.bart.hateoas.spring.builder;

import net.bart.hateoas.core.builders.AbstractResourceLinkBuilder;
import net.bart.hateoas.core.providers.HateoasProviderHelper;
import net.bart.hateoas.core.proxy.Invoker;

import java.lang.reflect.Method;
import java.net.URI;

public class SpringResourceLinkBuilder extends AbstractResourceLinkBuilder {

    @Override
    public <T> T getResource(final Class<T> resourceClass) {
        return HateoasProviderHelper.makeResourceWatcher(resourceClass, new InternalLinkBuilder());
    }

    private class InternalLinkBuilder implements Invoker {

        @Override
        public Object invoke(final Object proxy, final Method method, final Object[] arguments) throws Throwable {
            final String UriString = new ControllerClassUrlPart(method.getDeclaringClass()).getFinalResult()
                    + new MethodUrlPart(method).getFinalResult()
                    + new ParametersUrlPart(method).getFinalResult();
            setInternalLink(new URI(UriString));
            return null;
        }

    }

}
